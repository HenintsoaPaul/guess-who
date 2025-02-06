import React, { useState, useCallback, memo, useEffect } from 'react';
import { View, Text, StyleSheet, TextInput, TouchableOpacity, FlatList, ActivityIndicator } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import FavoriteButton from '../components/atoms/FavoriteButton';
import { db } from '../../config/firestore';
import { doc, updateDoc, getDoc, onSnapshot } from 'firebase/firestore';

const fetchWalletData = async (setCryptos, setWalletTotalPrice) => {
  try {
    const walletDocRef = doc(db, "wallets", "1");
    const docSnap = await getDoc(walletDocRef);

    if (docSnap.exists()) {
      const data = docSnap.data();
      setCryptos(data.wallets || []);
      setWalletTotalPrice(data.totalPrice);
      console.log('Données du document:', data);

      const unsubscribe = onSnapshot(walletDocRef, (doc) => {
        if (doc.exists()) {
          const updatedData = doc.data();
          setCryptos(updatedData.wallets || []);
          setWalletTotalPrice(updatedData.totalPrice);
          console.log('Données mises à jour:', updatedData);
        }
      });

      return unsubscribe;
    } else {
      console.log('Aucun document trouvé');
      setCryptos([]);
      setWalletTotalPrice(null);
    }
  } catch (error) {
    console.error('Erreur:', error);
    throw error;
  }
};

const fetchFavorites = async (setFavoritesList) => {
  try {
    const favoritesDocRef = doc(db, "favorites", "1");
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


const updateFavoritesListInFirestore = async (favoritesList) => {
  try {
    const favoritesDocRef = doc(db, "favorites", "1");  
    await updateDoc(favoritesDocRef, {
      favorites: favoritesList
    });
    console.log('Liste des favoris mise à jour dans Firestore:', favoritesList);
  } catch (error) {
    console.error('Erreur lors de la mise à jour de la liste des favoris dans Firestore:', error);
  }
};

const CoursActuelleScreen = () => {
  const navigation = useNavigation();
  const [favoritesList, setFavoritesList] = useState([]);
  const [cryptos, setCryptos] = useState([]);
  const [walletTotalPrice, setWalletTotalPrice] = useState(null);
  const [loading, setLoading] = useState(true);
  const [filterText, setFilterText] = useState('');

  useEffect(() => {
    const fetchCryptos = async () => {
      try {
        setLoading(true);
        const unsubscribe = await fetchWalletData(setCryptos, setWalletTotalPrice);
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
      await fetchFavorites(setFavoritesList);
    };

    fetchCryptos();
    fetchFavoritesData();
  }, []);

  const toggleFavorite = useCallback((index) => {
    setCryptos((prevCryptos) => {
      const updatedCryptos = prevCryptos.map((crypto, i) => 
        i === index 
          ? { ...crypto, isFavorite: !crypto.isFavorite } 
          : crypto
      );

      const updatedFavoritesList = updatedCryptos.filter(crypto => crypto.isFavorite);
      
      updateFavoritesListInFirestore(updatedFavoritesList);
      
      return updatedCryptos;
    });
  }, []);  

  const filteredCryptos = cryptos.filter((crypto) =>
    crypto.cryptoName.toLowerCase().includes(filterText.toLowerCase()) ||
    crypto.symbol.toLowerCase().includes(filterText.toLowerCase())
  );

  const CryptoItem = memo(({ item, index }) => (
    <View style={styles.tableRow}>
      <View style={styles.cell}>
        <Text style={styles.cellText}>{item.image} </Text>
      </View>
      <View style={styles.cell}>
        <Text style={styles.cellText}>{item.cryptoName}</Text>
      </View>
      <View style={styles.cell}>
        <Text style={styles.cellText}>{item.symbol}</Text>
      </View>
      <View style={styles.cell}>
        <Text style={styles.cellText}>{item.currentPrice} $</Text>
      </View>
      <View style={styles.cell}>
        <FavoriteButton
          isFavorite={favoritesList.some(fav => fav.cryptoName === item.cryptoName)} // Vérifie si la crypto est dans les favoris
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
        <Text style={styles.fundAmount}>{cryptos[0]?.fund || 0} €</Text>
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
