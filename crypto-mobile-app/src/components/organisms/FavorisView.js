import { View, Text, FlatList, StyleSheet } from 'react-native';
import React from 'react';

export default function FavorisView({ favorites }) {
  return (
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
  );
}

const styles = StyleSheet.create({
  favoriteItem: {
    padding: 10,
    borderBottomWidth: 1,
    borderBottomColor: '#ddd',
  },
  favoriteText: {
    fontSize: 16,
  },
});