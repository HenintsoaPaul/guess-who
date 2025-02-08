import React, { useState,useContext } from 'react';
import { View, Text, StyleSheet, TextInput,Button} from 'react-native';
import { FIRESTORE_DB } from '../services/firebaseService';
import { AppContext } from '../../AppContext';
import {Picker} from '@react-native-picker/picker';
import { collection ,doc,setDoc} from 'firebase/firestore';

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
  const [amount, setAmount] = useState('');
  const [transactionType, setTransactionType] = useState('depot');
  const {user} = useContext(AppContext);


  const handleTransaction = () => {
    if (!amount || isNaN(amount)) {
      alert("Veuillez entrer un montant valide.");
      return;
    }
    sendTransactionData(amount, transactionType, user);
  };

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
