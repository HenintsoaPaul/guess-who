import React, { useState, useEffect, useMemo } from 'react';
import { View, Text, FlatList, StyleSheet, ActivityIndicator } from 'react-native';
import { db } from '../../config/firestore';
import { collection, onSnapshot } from 'firebase/firestore';

const FavoritesScreen = () => {
  const [favorites, setFavorites] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    try {
      const favoritesCollection = collection(db, 'favorites');
      console.log("Collection reference:", favoritesCollection);

      const unsubscribe = onSnapshot(favoritesCollection, (snapshot) => {
        console.log("Snapshot received:", snapshot.docs.length);
        const favoritesList = snapshot.docs.map((doc) => ({
          id: doc.id,
          ...doc.data(),
        }));
        setFavorites(favoritesList);
        setLoading(false);
      });

      return () => unsubscribe();
    } catch (error) {
      console.error('Erreur lors de la récupération des favoris:', error);
      setError(error.message);
      setLoading(false);
    }
  }, []);

  const memoizedFavorites = useMemo(() => favorites, [favorites]);

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

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Mes Cryptos Favorites</Text>
      {memoizedFavorites.length > 0 ? (
        <FlatList
          data={memoizedFavorites}
          renderItem={({ item }) => (
            <View style={styles.favoriteItem}>
              <Text style={styles.cryptoName}>{item.cryptoName}</Text>
              <Text style={styles.symbol}>{item.symbol}</Text>
            </View>
          )}
          keyExtractor={(item) => item.id.toString()} 
        />
      ) : (
        <Text style={styles.noFavorites}>Aucune crypto en favori.</Text>
      )}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 16,
    backgroundColor: '#fff',
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 16,
    textAlign: 'center',
  },
  favoriteItem: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    padding: 12,
    borderBottomWidth: 1,
    borderBottomColor: '#ddd',
  },
  cryptoName: {
    fontSize: 16,
    fontWeight: 'bold',
  },
  symbol: {
    fontSize: 16,
    color: '#666',
  },
  noFavorites: {
    flex: 1,
    textAlign: 'center',
    marginTop: 20,
    color: '#666',
  },
  loadingText: {
    fontSize: 18,
    marginTop: 10,
    color: '#666',
  },
  errorText: {
    fontSize: 18,
    color: 'red',
    textAlign: 'center',
  },
});

export default FavoritesScreen;
