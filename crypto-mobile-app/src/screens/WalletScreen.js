import React, { useState, useEffect, useMemo, useContext } from 'react';
import { View, Text, FlatList, StyleSheet, TextInput, TouchableOpacity, ActivityIndicator } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { FIRESTORE_DB } from '../services/firebaseService';
import { onSnapshot, doc, getDoc } from 'firebase/firestore';
import { AppContext } from '../../AppContext';
import { query,getDocs ,collection ,where} from 'firebase/firestore';

const fetchWalletData = async (setCryptos, user) => {
  try {
    const walletQuery = query(collection(FIRESTORE_DB, "wallet"), where("account.id", "==", user.id));
    const querySnapshot = await getDocs(walletQuery);

    if (!querySnapshot.empty) {
      const wallets = [];

      querySnapshot.forEach((doc) => {
        const data = doc.data();
        wallets.push(data);
      });

      setCryptos(wallets);
      console.log('Données des documents:', wallets);

      // Listen for real-time updates
      const unsubscribe = onSnapshot(walletQuery, (snapshot) => {
        const updatedWallets = [];
        let updatedTotalPrice = 0;

        snapshot.forEach((doc) => {
          const updatedData = doc.data();
          updatedWallets.push(updatedData);
        });

        setCryptos(updatedWallets);
        console.log('Données mises à jour:', updatedWallets);
      });

      return unsubscribe;
    } else {
      console.log('Aucun wallet trouvé');
      setCryptos([]);
    }
  } catch (error) {
    console.error('Erreur:', error);
    throw error;
  }
};

export default function WalletScreen() {
  const {user} = useContext(AppContext) 
  const [cryptos, setCryptos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [filterText, setFilterText] = useState('');
  const navigation = useNavigation();

  useEffect(() => {
    const fetchCryptos = async () => {
      try {
        setLoading(true);
        setError(null);
        const unsubscribe = await fetchWalletData(setCryptos,user);
        return () => {
          if (unsubscribe) unsubscribe();
        };
      } catch (error) {
        setError(error.message);
      } finally {
        setLoading(false);
      }
    };
    fetchCryptos();
  }, []);

  const filteredCryptos = useMemo(
    () =>
      cryptos.filter(
        (crypto) =>
          crypto.crypto.name.toLowerCase().includes(filterText.toLowerCase()) ||
          crypto.crypto.symbol.toLowerCase().includes(filterText.toLowerCase())
      ),
    [cryptos, filterText]
  );

  if (loading) {
    return (
      <View style={styles.container}>
        <ActivityIndicator size="large" color="#000" />
        <Text style={styles.loadingText}>Chargement...</Text>
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
      <View style={styles.header}>
        <Text style={styles.title}>Portefeuille</Text>
        <View style={styles.fundContainer}>
          <Text style={styles.fundLabel}>Solde total :</Text>
          <Text style={styles.fundAmount}>{user.fund} €</Text>
        </View>
      </View>
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
        </View>
        {filteredCryptos.map((cpt, index) => (
          <TouchableOpacity
            key={index}
            style={styles.tableRow}
            onPress={() => navigation.navigate('CryptotDetail', { cpt })}
          >
            <View style={styles.cell}>
              <Text style={styles.cellText}></Text>
            </View>
            <View style={styles.cell}>
              <Text style={styles.cellText}>{cpt.crypto.name}</Text>
            </View>
            <View style={styles.cell}>
              <Text style={styles.cellText}>{cpt.crypto.symbol}</Text>
            </View>
            <View style={styles.cell}>
              <Text style={styles.cellText}>{cpt.quantity}</Text>
            </View>
          </TouchableOpacity>
        ))}
      </View>
    </View>
  );
}

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