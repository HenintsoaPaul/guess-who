import React, { useState, useCallback, memo, useEffect, useContext } from 'react';
import { View, Text, StyleSheet, TextInput, TouchableOpacity, FlatList, ActivityIndicator } from 'react-native';
import FavoriteButton from '../components/atoms/FavoriteButton';
import { doc, updateDoc, getDocs, onSnapshot , collection,orderBy,query,limit} from 'firebase/firestore';
import { Timestamp } from 'firebase/firestore';
import {FIRESTORE_DB } from '../services/firebaseService';
import { AppContext } from '../../AppContext';

const fetchCoursData = async (setCryptos) => {
  try {
    const coursCollectionRef = collection(FIRESTORE_DB, "cours");
    const querySnapshot = query(coursCollectionRef, orderBy("dateCours", "desc"), limit(10));
    const unsubscribe = onSnapshot(querySnapshot,(snapshot) => {
      const updatedCryptos = [];
      snapshot.forEach((doc) => {
        updatedCryptos.push(doc.data());
      });
      setCryptos(updatedCryptos);
      console.log('Données mises à jour:', updatedCryptos);
    });
    return unsubscribe;
  } catch (error) {
    console.error('Erreur:', error);
    throw error;
  }
};

const fetchFavorites = async (setFavoritesList,user) => {
  try {
    const favoritesDocRef = doc(FIRESTORE_DB, "favorites", user.id+"");
    const unsubscribe = onSnapshot(favoritesDocRef, (docSnap) => {
      if (docSnap.exists()) {
        const data = docSnap.data();
        setFavoritesList(data.favorites || []);
        console.log('Données des favoris mises à jour:', data);
      } else {
        console.log('Aucun document trouvé pour les favoris');
        setFavoritesList([]);
      }
    });

    return unsubscribe;
  } catch (error) {
    console.error('Erreur lors du chargement des favoris:', error);
  }
};

const addFavorite = async (cryptoId, accountId) => {
  try {
    const response = await fetch('http://localhost:8080/api/crypto-fav/add', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ cryptoId, accountId }),
    });

    if (!response.ok) throw new Error('Erreur lors de l’ajout aux favoris');

    console.log('Favori ajouté avec succès !');
  } catch (error) {
    console.error('Erreur:', error);
  }
};

const removeFavorite = async (cryptoId, accountId) => {
  try {
    const response = await fetch('http://localhost:8080/api/crypto-fav/remove', {
      method: 'DELETE',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ cryptoId, accountId }),
    });

    if (!response.ok) throw new Error('Erreur lors de la suppression du favori');

    console.log('Favori supprimé avec succès !');
  } catch (error) {
    console.error('Erreur:', error);
  }
};

const updateFavoritesListInFirestore = async (favoritesList,user) => {
  try {
    const favoritesDocRef = doc(FIRESTORE_DB, "favorites", user.id+"");  
    await updateDoc(favoritesDocRef, {
      favorites: favoritesList
    });

    const updatedFavorites = favoritesList.map(fav => ({
      ...fav,
      dateAdded: fav.dateAdded || Timestamp.now(), 
    }));

    if (updatedCrypto.isFavorite) {
      addFavorite(updatedCrypto.id, accountId); 
    } else {
      removeFavorite(updatedCrypto.id, accountId);
    }

    await updateDoc(favoritesDocRef, {
      favorites: updatedFavorites
    });

    console.log('Liste des favoris mise à jour dans Firestore:', favoritesList);
  } catch (error) {
    console.error('Erreur lors de la mise à jour de la liste des favoris dans Firestore:', error);
  }
};

const CoursActuelleScreen = () => {
  const [favoritesList, setFavoritesList] = useState([]);
  const [cryptos, setCryptos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [filterText, setFilterText] = useState('');
  const {user} = useContext(AppContext);

  useEffect(() => {
    const fetchCryptos = async () => {
      setLoading(true);
      try {
        const unsubscribe = await fetchCoursData(setCryptos);
        return () => {
          if (unsubscribe) unsubscribe();
        };
      } catch (error) {
        console.error('Erreur lors du chargement des cryptomonnaies:', error);
      } finally {
        setLoading(false);
      }
    };

    const fetchFavoritesData = async () => {
      await fetchFavorites(setFavoritesList,user);
    };

    // fetchCryptos();
    // fetchFavoritesData();
  }, []);

  const toggleFavorite = useCallback((index) => {
    setCryptos((prevCryptos) => {
      const updatedCryptos = prevCryptos.map((crypto, i) => {
        if (i === index) {
          return { 
            ...crypto, 
            isFavorite: !crypto.isFavorite,
            dateAdded: crypto.isFavorite ? null : Timestamp.now(), 
          };
        }
        return crypto;
      });
  
      const updatedFavoritesList = updatedCryptos
        .filter(crypto => crypto.isFavorite)
        .map(crypto => ({
          ...crypto,
          dateAdded: crypto.dateAdded || Timestamp.now(), 
        }));
  
      updateFavoritesListInFirestore(updatedFavoritesList,user);
  
      return updatedCryptos;
    });
  }, []);    

  const filteredCryptos = cryptos.filter((crypto) =>
    crypto.crypto.name.toLowerCase().includes(filterText.toLowerCase()) ||
    crypto.crypto.symbol.toLowerCase().includes(filterText.toLowerCase())
  );

  const CryptoItem = memo(({ item, index }) => (
    <View style={styles.tableRow}>
      <View style={styles.cell}>
        <Text style={styles.cellText}>{item.image} </Text>
      </View>
      <View style={styles.cell}>
        <Text style={styles.cellText}>{item.crypto.name}</Text>
      </View>
      <View style={styles.cell}>
        <Text style={styles.cellText}>{item.crypto.symbol}</Text>
      </View>
      <View style={styles.cell}>
        <Text style={styles.cellText}>{item.pu} $</Text>
      </View>
      <View style={styles.cell}>
        <FavoriteButton
          isFavorite={favoritesList.some(fav => fav.cryptoName === item.crypto.name)} // Vérifie si la crypto est dans les favoris
          onPress={() => toggleFavorite(index)}
        />
      </View>
    </View>
  ));

  if (loading) {
    return (
      <View style={styles.container}>
        <ActivityIndicator size="large" color="#000" />
        <Text style={styles.loadingText}>Chargement...</Text>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      {/* Affichage du solde total */}
      <View style={styles.fundContainer}>
        <Text style={styles.fundLabel}>Solde total :</Text>
        <Text style={styles.fundAmount}>{user.fund || 0} €</Text>
      </View>

      {/* Barre de recherche */}
      <View style={styles.searchContainer}>
        <TextInput
          style={styles.searchInput}
          placeholder="Rechercher une cryptomonnaie..."
          value={filterText}
          onChangeText={(text) => setFilterText(text)}
        />
      </View>

      {/* Tableau des cryptomonnaies */}
      <View style={styles.table}>
        <View style={styles.tableHeader}>
          <View style={styles.headerCell}>
            <Text style={styles.headerText}>Image</Text>
          </View>
          <View style={styles.headerCell}>
            <Text style={styles.headerText}>Cryptomonnaie</Text>
          </View>
          <View style={styles.headerCell}>
            <Text style={styles.headerText}>Symbole</Text>
          </View>
          <View style={styles.headerCell}>
            <Text style={styles.headerText}>Cours Actuel</Text>
          </View>
          <View style={styles.headerCell}>
            <Text style={styles.headerText}>Favoris</Text>
          </View>
        </View>
        {filteredCryptos.map((crypto, index) => (
          <CryptoItem key={index} item={crypto} index={index} />
        ))}
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'flex-start',
    alignItems: 'center',
    padding: 16,
  },
  header: {
    width: '100%',
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 16,
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
  },
  fundContainer: {
    width: '100%',
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 16,
    padding: 8,
    backgroundColor: '#f5f5f5',
    borderRadius: 4,
  },
  fundLabel: {
    fontSize: 16,
    color: '#666',
  },
  fundAmount: {
    fontSize: 16,
    fontWeight: 'bold',
  },
  searchContainer: {
    width: '100%',
    marginBottom: 16,
  },
  searchInput: {
    width: '100%',
    padding: 12,
    backgroundColor: '#f5f5f5',
    borderRadius: 4,
    borderWidth: 1,
    borderColor: '#ddd',
  },
  table: {
    width: '100%',
    borderWidth: 1,
    borderColor: '#ddd',
    borderRadius: 4,
  },
  tableHeader: {
    flexDirection: 'row',
    backgroundColor: '#f5f5f5',
    borderBottomWidth: 1,
    borderBottomColor: '#ddd',
  },
  tableRow: {
    flexDirection: 'row',
    borderBottomWidth: 1,
    borderBottomColor: '#ddd',
  },
  headerCell: {
    flex: 1,
    padding: 12,
    justifyContent: 'center',
    alignItems: 'center',
  },
  cell: {
    flex: 1,
    padding: 12,
    justifyContent: 'center',
    alignItems: 'center',
  },
  headerText: {
    fontWeight: 'bold',
    color: '#333',
  },
  cellText: {
    color: '#666',
  }
});

export default CoursActuelleScreen;
