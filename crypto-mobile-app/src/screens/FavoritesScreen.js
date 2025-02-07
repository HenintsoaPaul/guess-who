import React, { useState, useEffect, useMemo, useContext } from 'react';
import { View, Text, StyleSheet, ActivityIndicator, TouchableOpacity } from 'react-native';
import { doc, onSnapshot, getDoc } from 'firebase/firestore';
import { AppContext } from '../../AppContext';
import { FIRESTORE_DB } from '../services/firebaseService';


const fetchWalletData = async (setFavorites,user) => {
  try {
    const Listfav = doc(FIRESTORE_DB, "favorites", user.id+"");
    const docSnap = await getDoc(Listfav);

    if (docSnap.exists()) {
      const data = docSnap.data();
      setFavorites(data.favorites || []);
      console.log('Données du document:', data);

      const unsubscribe = onSnapshot(Listfav, (doc) => {
        if (doc.exists()) {
          const updatedData = doc.data();
          setFavorites(updatedData.favorites || []);
          console.log('Données mises à jour:', updatedData);
        }
      });

      return unsubscribe;
    } else {
      console.log('Aucun document trouvé');
      setFavorites([]);
    }
  } catch (error) {
    console.error('Erreur:', error);
    throw error;
  }
};

const FavoritesScreen = () => {
  const [favorites, setFavorites] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [filterText, setFilterText] = useState('');
  const {user} = useContext(AppContext);

  useEffect(() => {
    const fetchFavoris = async () => {
      try {
        setLoading(true);
        setError(null);
        const unsubscribe = await fetchWalletData(setFavorites,user);
        return () => {
          if (unsubscribe) unsubscribe();
        };
      } catch (error) {
        setError(error.message);
      } finally {
        setLoading(false);
      }
    };
    fetchFavoris();
  }, []);

  const filteredFavoris = useMemo(
    () =>
      favorites.filter(
        (favorite) =>
          favorite.cryptoName.toLowerCase().includes(filterText.toLowerCase()) ||
          favorite.symbol.toLowerCase().includes(filterText.toLowerCase())
      ),
    [favorites, filterText]
  );

  if (loading) {
    return (
      <View style={styles.container}>
        <ActivityIndicator size="large" color="#000" />
        <Text style={styles.loadingText}>Chargement des favoris...</Text>
      </View>
    );
  }

  if (error) {
    return (
      <View style={styles.container}>
        <Text style={styles.errorText}>Erreur: {error}</Text>
      </View>
    );
  }
  console.log(favorites);
  return (
    <View style={styles.container}>
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
          </View>
          {filteredFavoris.map((favorite, index) => (
          <TouchableOpacity
            key={index}
            style={styles.tableRow}
          >
            <View style={styles.cell}>
              <Text style={styles.cellText}></Text>
            </View>
            <View style={styles.cell}>
              <Text style={styles.cellText}>{favorite.cryptoName}</Text>
            </View>
            <View style={styles.cell}>
              <Text style={styles.cellText}>{favorite.symbol}</Text>
            </View>
          </TouchableOpacity>
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
  title: {
    fontSize: 24,
    marginBottom: 20,
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
  },
  favoriteText: {
    color: '#FFD700', 
    fontSize: 16,
  },
  navigateButton: {
    marginTop: 16,
    padding: 12,
    backgroundColor: '#007AFF',
    borderRadius: 4,
    width: 160,
  },
  buttonText: {
    color: 'white',
    textAlign: 'center',
  },
});

export default FavoritesScreen;
