import React, {useEffect} from 'react';
import { View, Text, StyleSheet, FlatList } from 'react-native';

const FavoritesScreen = ({ route }) => {
  const { favoritesList } = route.params;
  useEffect(() => {
    console.log('Données reçues dans FavoritesScreen:', route.params);
  }, [route.params]);

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Mes Favoris</Text>
      
      {favoritesList.length > 0 ? (
        <FlatList
          data={favoritesList}
          renderItem={({ item }) => (
            <View style={styles.favoriteItem}>
              <Text>{item.cryptoName}</Text>
              <Text>{item.symbol}</Text>
              <Text>{item.currentPrice}$</Text>
            </View>
          )}
          keyExtractor={item => item.idAccount.toString()}
        />
      ) : (
        <Text style={styles.noFavorites}>Aucune crypto en favori pour le moment.</Text>
      )}
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
    marginBottom: 16,
  },
  favoriteItem: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    padding: 12,
    borderBottomWidth: 1,
    borderBottomColor: '#ddd',
  },
  noFavorites: {
    flex: 1,
    textAlign: 'center',
    marginTop: 20,
    color: '#666',
  }
});

export default FavoritesScreen;