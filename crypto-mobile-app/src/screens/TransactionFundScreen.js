import React, { useState, useEffect, useMemo } from 'react';
import { View, Text, FlatList, StyleSheet, TextInput, TouchableOpacity, ActivityIndicator } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { db } from '../../config/firestore';
import { onSnapshot, doc, getDoc } from 'firebase/firestore';

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

export default function WalletScreen() {
  const [cryptos, setCryptos] = useState([]);
  const [walletTotalPrice, setWalletTotalPrice] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [filterText, setFilterText] = useState('');
  const navigation = useNavigation();
  const [amount, setAmount] = useState('');
  const [transactionType, setTransactionType] = useState('deposit');

  useEffect(() => {
    const fetchCryptos = async () => {
      try {
        setLoading(true);
        setError(null);
        const unsubscribe = await fetchWalletData(setCryptos, setWalletTotalPrice);
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
          crypto.cryptoName.toLowerCase().includes(filterText.toLowerCase()) ||
          crypto.symbol.toLowerCase().includes(filterText.toLowerCase())
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
        <View style={styles.fundContainer}>
          <Text style={styles.fundLabel}>Solde total :</Text>
          <Text style={styles.fundAmount}>{cryptos[0].fund} €</Text>
        </View>
      </View>

      <View style={styles.inputContainer}>
        <TextInput
          style={styles.amountInput}
          keyboardType="numeric"
          placeholder="Montant"
          value={amount}
          onChangeText={(text) => setAmount(text)}
        />
      </View>

      <View style={styles.selectorContainer}>
        <Picker
          selectedValue={transactionType}
          style={styles.transactionSelector}
          onValueChange={(itemValue) => setTransactionType(itemValue)}
        >
          <Picker.Item label="Dépôt" value="deposit" />
          <Picker.Item label="Retrait" value="withdrawal" />
        </Picker>
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
  fundContainer: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginBottom: 20,
  },
  inputContainer: {
    marginBottom: 20,
  },
  amountInput: {
    height: 40,
    borderColor: '#ccc',
    borderWidth: 1,
    paddingHorizontal: 10,
    borderRadius: 5,
  },
  selectorContainer: {
    borderWidth: 1,
    borderColor: '#ccc',
    borderRadius: 5,
    overflow: 'hidden',
  },
  transactionSelector: {
    width: '100%',
  }
});