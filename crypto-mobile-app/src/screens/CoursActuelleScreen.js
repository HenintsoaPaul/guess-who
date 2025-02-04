import React, { useState } from 'react';
import { View, Text, StyleSheet, TextInput, TouchableOpacity } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import FavoriteButton from '../components/atoms/FavoriteButton';

const CoursActuelleScreen = () => {
  const navigation = useNavigation();
  
  const [cryptos, setCryptos] = useState([
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
      currentPrice: 45000,
      value: 45000 * 1000,
      lastUpdated: "2023-10-01",
      isFavorite: false,
    },
    {
      idAccount: 2, // Identifiant unique pour Ethereum
      pseudo: "Alice",
      image: "image2",
      fund: 10000.0,
      cryptoName: "Ethereum",
      symbol: "ETH",
      quantity: 500,
      purchaseDate: null,
      totalPrice: null,
      currentPrice: 3000,
      value: 3000 * 500,
      lastUpdated: "2023-10-01",
      isFavorite: false,
    }
  ]);

  const toggleFavorite = (idAccount) => {
    setCryptos((prevCryptos) =>
      prevCryptos.map((crypto) =>
        crypto.idAccount === idAccount
          ? { ...crypto, isFavorite: !crypto.isFavorite }
          : crypto
      )
    );
  };

  const [filterText, setFilterText] = useState('');
  
  // Filtrage des cryptos en fonction du texte saisi
  const filteredCryptos = cryptos.filter((crypto) =>
    crypto.cryptoName.toLowerCase().includes(filterText.toLowerCase()) ||
    crypto.symbol.toLowerCase().includes(filterText.toLowerCase())
  );

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Portefeuille</Text>

      {/* Affichage du solde total */}
      <View style={styles.fundContainer}>
        <Text style={styles.fundLabel}>Solde total :</Text>
        <Text style={styles.fundAmount}>{cryptos[0].fund} €</Text>
      </View>

      {/* Barre de recherche */}
      <View style={styles.searchContainer}>
        <TextInput
          style={styles.searchInput}
          placeholder="Rechercher une cryptomonnaie..."
          value={filterText}
          onChange={(text) => setFilterText(text)}
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
            <Text style={styles.headerText}>Quantité</Text>
          </View>
          <View style={styles.headerCell}>
            <Text style={styles.headerText}>Cours Actuel</Text>
          </View>
          <View style={styles.headerCell}>
            <Text style={styles.headerText}>Valeur</Text>
          </View>
          <View style={styles.headerCell}>
            <Text style={styles.headerText}>Date MAJ</Text>
          </View>
          <View style={styles.headerCell}>
            <Text style={styles.headerText}>Favoris</Text>
          </View>
        </View>

        {filteredCryptos.map((crypto, index) => (
          <View key={index} style={styles.tableRow}>
            <View style={styles.cell}>
              <Text style={styles.cellText}>{crypto.image} </Text>
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
            <View style={styles.cell}>
              <Text style={styles.cellText}>{crypto.currentPrice} $</Text>
            </View>
            <View style={styles.cell}>
              <Text style={styles.cellText}>{crypto.value} $</Text>
            </View>
            <View style={styles.cell}>
              <Text style={styles.cellText}>{crypto.lastUpdated}</Text>
            </View>
            <View style={styles.cell}>
              <FavoriteButton
                isFavorite={crypto.isFavorite}
                onPress={() => toggleFavorite(crypto.idAccount)}
              />
            </View>
          </View>
        ))}
      </View>

      {/* Bouton pour naviguer vers les favoris */}
      <TouchableOpacity
        style={styles.navigateButton}
        onPress={() => {
          const favorites = cryptos.filter((crypto) => crypto.isFavorite);
          if (favorites.length > 0) {
            navigation.navigate('Favorites', { favorites });
          } else {
            alert('Aucune crypto n\'est marquée comme favorite');
          }
        }}
      >
        <Text style={styles.buttonText}>Voir mes favoris</Text>
      </TouchableOpacity>
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
  
  export default CoursActuelleScreen;

