import React, { useState, useEffect } from 'react';
import { View, Text, StyleSheet, FlatList ,TouchableOpacity} from 'react-native';
import NoFavoris from '../components/molecules/NoFavoris';
import FavorisView from '../components/organisms/FavorisView';

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
  return (
    <View style={styles.container}>
      <Text style={styles.title}>Mes Favoris</Text>
      {favorites.length === 0 && (
        <NoFavoris />
      )}
      <FavorisView />
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
});

export default FavoritesScreen;