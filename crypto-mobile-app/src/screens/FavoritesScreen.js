import React, { useState, useEffect } from 'react';
import { View, Text, StyleSheet, FlatList } from 'react-native';

const FavoritesScreen = ({ route, navigation }) => {
  const [favorites, setFavorites] = useState([]);
  
  const fetchFavorites = async () => {
    try {
      const mockData = [
        { idAccount: 1, cryptoName: 'Bitcoin', symbol: 'BTC' },
        { idAccount: 2, cryptoName: 'Ethereum', symbol: 'ETH' },
        { idAccount: 3, cryptoName: 'Cardano', symbol: 'ADA' },
      ];
      setFavorites(mockData);
    } catch (error) {
      console.error('Erreur lors de la récupération des favoris:', error);
    }
  };

  useEffect(() => {
    fetchFavorites();
  }, []);

  const handleAddFavorite = () => {
    const newFavorite = {
      idAccount: Date.now(),
      cryptoName: 'Nouvelle Crypto',
      symbol: 'NEW'
    };
    setFavorites([...favorites, newFavorite]);
  };

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Mes Favoris</Text>
      
      <TouchableOpacity 
        style={styles.addButton}
        onPress={handleAddFavorite}
      >
        <Text style={styles.addButtonText}>Ajouter un favori</Text>
      </TouchableOpacity>

      {favorites.length === 0 && (
        <Text style={styles.noFavoritesText}>
          Aucune crypto n'a été ajoutée aux favoris
        </Text>
      )}

      <FlatList
        data={favorites}
        keyExtractor={(item) => item.idAccount.toString()}
        renderItem={({ item }) => (
          <View style={styles.favoriteItem}>
            <Text style={styles.favoriteText}>
              {item.cryptoName} ({item.symbol})
            </Text>
          </View>
        )}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 16,
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 20,
  },
  addButton: {
    padding: 12,
    backgroundColor: '#007AFF',
    borderRadius: 4,
    marginBottom: 16,
  },
  addButtonText: {
    color: 'white',
    textAlign: 'center',
  },
  favoriteItem: {
    padding: 12,
    borderBottomWidth: 1,
    borderBottomColor: '#ddd',
  },
  favoriteText: {
    fontSize: 16,
  },
  noFavoritesText: {
    padding: 16,
    textAlign: 'center',
    color: '#666',
  },
});

export default FavoritesScreen;