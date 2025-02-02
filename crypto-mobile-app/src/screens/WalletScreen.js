import React, { useState, useCallback } from 'react';
import { View, Text, StyleSheet, TextInput, TouchableOpacity, Alert } from 'react-native';
import { useNavigation } from '@react-navigation/native';

const WalletScreen = () => {
  const navigation = useNavigation();

  const [cryptos] = useState([
    {
      idAccount: 1,
      pseudo: "Alice",
      image: "image1",
      fund: 10000.0,
      cryptoName: "Bitcoin",
      symbol: "BTC",
      quantity: 1000,
      purchaseDate: null,
      totalPrice: null,
      isFavorite: false // Propriété ajoutée
    },
    {
      idAccount: 1,
      pseudo: "Alice",
      image: "image2",
      fund: 10000.0,
      cryptoName: "Ethereum",
      symbol: "ETH",
      quantity: 500,
      purchaseDate: null,
      totalPrice: null,
      isFavorite: false // Propriété ajoutée
    }
  ]);

  const [filterText, setFilterText] = useState('');

  // Fonction mémorisée pour filtrer les cryptomonnaies
  const filteredCryptos = useCallback(() => {
    return cryptos.filter(crypto =>
      crypto.cryptoName.toLowerCase().includes(filterText.toLowerCase()) ||
      crypto.symbol.toLowerCase().includes(filterText.toLowerCase())
    );
  }, [cryptos, filterText]);

  // Gestionnaire d'événements pour les favoris
  const handleFavorites = useCallback(() => {
    const favorites = cryptos.filter(crypto => crypto.isFavorite);
    if (favorites.length > 0) {
      navigation.navigate('Favorites', { favorites });
    } else {
      Alert.alert('Information', 'Aucune crypto n\'est marquée comme favorite');
    }
  }, [cryptos, navigation]);

  return (
    <View style={styles.container}>
      {/* En-tête avec titre et solde */}
      <View style={styles.header}>
        <Text style={styles.title}>Portefeuille</Text>
        
        {/* Section solde */}
        <View style={styles.fundContainer}>
          <Text style={styles.fundLabel}>Solde total :</Text>
          <Text style={styles.fundAmount}>{cryptos[0].fund} €</Text>
        </View>
      </View>

      {/* Zone de recherche */}
      <View style={styles.searchContainer}>
        <TextInput
          style={styles.searchInput}
          placeholder="Rechercher une cryptomonnaie..."
          value={filterText}
          onChangeText={setFilterText}
          autoCapitalize="none"
          autoCorrect={false}
        />
      </View>

      {/* Tableau des cryptomonnaies */}
      <View style={styles.table}>
        {/* Entête du tableau */}
        <View style={styles.tableHeader}>
          <View style={styles.headerCell}><Text style={styles.headerText}>Image</Text></View>
          <View style={styles.headerCell}><Text style={styles.headerText}>Cryptomonnaie</Text></View>
          <View style={styles.headerCell}><Text style={styles.headerText}>Symbole</Text></View>
          <View style={styles.headerCell}><Text style={styles.headerText}>Quantité</Text></View>
        </View>

        {/* Lignes du tableau */}
        {filteredCryptos().map((crypto, index) => (
          <TouchableOpacity 
            key={index}
            onPress={() => navigation.navigate('CryptoDetail', { crypto })}
            style={styles.tableRow}
            activeOpacity={0.7}
          >
            <View style={styles.cell}>
              <Text style={styles.cellText}>{crypto.image}</Text>
            </View>
            <View style={styles.cell}>
              <Text style={styles.cellText}>{crypto.cryptoName}</Text>
            </View>
            <View style={styles.cell}>
              <Text style={styles.cellText}>{crypto.symbol}</Text>
            </View>
            <View style={styles.cell}>
              <Text style={styles.cellText}>{crypto.quantity}</Text>
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

export default WalletScreen;
