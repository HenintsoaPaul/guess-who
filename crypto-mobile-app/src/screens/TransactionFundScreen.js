import React, { useState, useEffect, useMemo, useContext } from 'react';
import { View, Text, StyleSheet, TextInput, Picker, ActivityIndicator, Button} from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { onSnapshot, doc, getDoc, setDoc, collection } from 'firebase/firestore';
import { FIRESTORE_DB } from '../services/firebaseService';
import { AppContext } from '../../AppContext';

const fetchWalletData = async (setCryptos, setWalletTotalPrice) => {
  try {
    const walletDocRef = doc(FIRESTORE_DB, "wallets", "1");
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

const sendTransactionData = async (amount, transactionType, user) => {
  try {
    const transactionRef = collection(FIRESTORE_DB, 'transactions');
    const timestamp = Date.now();
    const transactionData = {
      date: timestamp,
      etat: 1, 
      data: {
        user: user.id,
        type: transactionType, 
        montant: parseFloat(amount)
      },
      response: {} 
    };

    await setDoc(doc(transactionRef), transactionData);
    console.log('Transaction envoyée:', transactionData);
  } catch (error) {
    console.error('Erreur lors de l\'envoi de la transaction:', error);
  }
};

function TransactionFundScreen() {
  const [cryptos, setCryptos] = useState([]);
  const [walletTotalPrice, setWalletTotalPrice] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [filterText, setFilterText] = useState('');
  const navigation = useNavigation();
  const [amount, setAmount] = useState('');
  const [transactionType, setTransactionType] = useState('deposit');
  const {user, setUser} = useContext(AppContext); 

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

  const handleTransaction = () => {
    if (!amount || isNaN(amount)) {
      alert("Veuillez entrer un montant valide.");
      return;
    }
    sendTransactionData(amount, transactionType, user);
  };

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
          <Text style={styles.fundAmount}>{user.fund} €</Text>
        </View>
      </View>

      <View style={styles.formContainer}>
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
            <Picker.Item label="Dépôt" value="depot" />
            <Picker.Item label="Retrait" value="Retrait" />
            </Picker>
        </View>
      </View>

      <View style={styles.buttonContainer}>
        <Button title="Confirmer la transaction" onPress={handleTransaction} />
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
  },
  buttonContainer: {
    marginTop: 20,
  }
});

export default TransactionFundScreen;
