import React, { useState,useContext } from 'react';
import { View, Text, StyleSheet, TextInput,Button} from 'react-native';
import { FIRESTORE_DB, updateOrCreateMobDoc } from '../services/firebaseService';
import { AppContext } from '../../AppContext';
import {Picker} from '@react-native-picker/picker';
import { collection, doc, setDoc, Timestamp } from 'firebase/firestore';
import { colorsChart } from '../constants/ColorsChart';

const sendTransactionData = async (amount, transactionType, user) => {
  try {
    const transactionData = {
      amount:parseFloat(amount),
      id:null,
      datePending: new Date().toISOString(),
      dateValidation:null,
      account:{id:user.id},
      typeMvFund:{id:Number.parseInt(transactionType)},
      pendingState:{id:1}
    }
    await updateOrCreateMobDoc("pending_mv_fund",transactionData,null)
    console.log('Transaction envoyée:', transactionData);
  } catch (error) {
    console.error('Erreur lors de l\'envoi de la transaction:', error);
  }
};

function TransactionFundScreen() {
  const [amount, setAmount] = useState('');
  const [transactionType, setTransactionType] = useState(1);
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
      <View >
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
            itemStyle={{color:colorsChart.primary,height:100}}
            selectedValue={transactionType}
            style={styles.transactionSelector}
            onValueChange={(itemValue) => setTransactionType(itemValue)}
            >
            <Picker.Item label="Dépôt" value={1} />
            <Picker.Item label="Retrait" value={2} />
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
    backgroundColor:colorsChart.white,
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
  formContainer:{
    width:'100%',
    backgroundColor:colorsChart.white
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
    flex:1,
  },
  buttonContainer: {
    marginTop: 20,
  }
});

export default TransactionFundScreen;
