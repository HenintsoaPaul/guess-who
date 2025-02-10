import React, { useState, useEffect, useContext } from 'react';
import {
  View,
  Text,
  StyleSheet,
  TextInput,
  TouchableOpacity,
  FlatList,
  ActivityIndicator,
  Platform,
} from 'react-native';
import { fetchDataFromFirebase, firebaseCollection} from '../services/firebaseService';
import { FontAwesome } from '@expo/vector-icons'; // Import FontAwesome for the search icon
import DateTimePicker from '@react-native-community/datetimepicker'; // Import DateTimePicker
import { colorsChart } from '../constants/ColorsChart';
import PurchaseCard from '../components/molecules/PurchaseCard';
import { onSnapshot } from 'firebase/firestore';
import { AppContext } from '../../AppContext';


const unsubscribePurchase = (setPurchases,setFilteredPurchases) => {
  const purchaseRef = firebaseCollection('purchase');
  const unsubscribe = onSnapshot(purchaseRef,(snapshot) => {
    const uptPurchases = [];
    snapshot.forEach((doc) => {
      uptPurchases.push(doc.data());
    });
    setPurchases(uptPurchases);
    setFilteredPurchases(uptPurchases);
  });
  return unsubscribe;
}
const AchatVenteScreen = () => {
  const [purchases, setPurchases] = useState([]);
  const [filteredPurchases, setFilteredPurchases] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [crypto, setCrypto] = useState('');
  const [startDate, setStartDate] = useState(null);
  const [endDate, setEndDate] = useState(null);
  const [showSearch, setShowSearch] = useState(false); // State to toggle search area
  const [showStartDatePicker, setShowStartDatePicker] = useState(false);
  const [showEndDatePicker, setShowEndDatePicker] = useState(false);
  const {user} = useContext(AppContext);

  if(user === null) {
    return <></>
  }

  useEffect(() => {
    const fetchPurchases = async () => {
      try {
        const purchases =  await fetchDataFromFirebase('purchase',null)
        
        setPurchases(purchases)
        setFilteredPurchases(purchases);

        const unsubscribe = unsubscribePurchase(setPurchases,setFilteredPurchases);
        return () => {
          if (unsubscribe) unsubscribe();
        };
      } catch (err) {
        setError(err);
        alert('Erreur:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchPurchases();
  }, []);

  const handleSearch = () => {
    let filtered = purchases;
    if (startDate) {
      filtered = filtered.filter(p =>
        new Date(p.datePurchase?.seconds * 1000) >= startDate
      );
    }
    if (endDate) {
      filtered = filtered.filter(p =>
        new Date(p.datePurchase?.seconds * 1000) <= endDate
      );
    }
    if (crypto) {
      filtered = filtered.filter(p => {
        const cryptoName = (p.saleDetailDocument?.crypto?.name || p.crypto?.name || '').toLowerCase();
        return cryptoName.includes(crypto.toLowerCase());
      });
    }
    setFilteredPurchases(filtered);
  };

  const handleReset = () => {
    setCrypto('');
    setStartDate(null);
    setEndDate(null);
    setFilteredPurchases(purchases);
  };

  const onStartDateChange = (event, selectedDate) => {
    const currentDate = selectedDate || startDate;
    setShowStartDatePicker(Platform.OS === 'ios');
    setStartDate(currentDate);
  };

  const onEndDateChange = (event, selectedDate) => {
    const currentDate = selectedDate || endDate;
    setShowEndDatePicker(Platform.OS === 'ios');
    setEndDate(currentDate);
  };

  if (loading) {
    return (
      <View style={styles.centered}>
        <ActivityIndicator size="large" color={colorsChart.dark} />
        <Text style={styles.loadingText}>Chargement...</Text>
      </View>
    );
  }

  if (error) {
    return (
      <View style={styles.centered}>
        <Text style={styles.errorText}>{error}</Text>
      </View>
    );
  }

  const renderItem = ({ item }) => {
    return <PurchaseCard purchase={item}></PurchaseCard>
  };

  return (
    <View style={styles.container}>
      {/* Search button at the top */}
      {!showSearch && (
        <TouchableOpacity
          style={styles.searchToggleButton}
          onPress={() => setShowSearch(!showSearch)}
          accessible={true}
          accessibilityLabel="Afficher ou masquer la zone de recherche"
        >
          <FontAwesome name="chevron-down" size={16} color={styles.searchToggleButtonText.color} />
          <Text style={styles.searchToggleButtonText}> Rechercher</Text>
        </TouchableOpacity>
      )}

      {/* Zone de recherche – Design épuré */}
      {showSearch && (
        <View style={styles.searchContainer}>
          <View style={styles.inputGroup}>
            {/* Le champ Crypto est désormais placé en haut */}
            <TextInput
              style={styles.input}
              placeholder="Crypto"
              placeholderTextColor={styles.placeholder.color}
              value={crypto}
              onChangeText={setCrypto}
              accessible={true}
              accessibilityLabel="Nom du crypto"
            />
          </View>
          <View style={styles.inputGroup}>
            <TouchableOpacity onPress={() => setShowStartDatePicker(true)} style={styles.dateInput}>
              <Text style={styles.dateText}>{startDate ? startDate.toLocaleDateString() : 'Date début'}</Text>
            </TouchableOpacity>
            {showStartDatePicker && (
              <DateTimePicker
                value={startDate || new Date()}
                mode="date"
                display="default"
                onChange={onStartDateChange}
              />
            )}
            <TouchableOpacity onPress={() => setShowEndDatePicker(true)} style={styles.dateInput}>
              <Text style={styles.dateText}>{endDate ? endDate.toLocaleDateString() : 'Date fin'}</Text>
            </TouchableOpacity>
            {showEndDatePicker && (
              <DateTimePicker
                value={endDate || new Date()}
                mode="date"
                display="default"
                onChange={onEndDateChange}
              />
            )}
          </View>
          <View style={styles.buttonRow}>
            <TouchableOpacity
              style={[styles.button, styles.resetButton]}
              onPress={handleSearch}
              accessible={true}
              accessibilityLabel="Rechercher"
            >
              <Text style={[styles.buttonText, styles.buttonText]}>Rechercher</Text>
            </TouchableOpacity>
            <TouchableOpacity
              style={[styles.button, styles.outlineButton]}
              onPress={handleReset}
              accessible={true}
              accessibilityLabel="Réinitialiser"
            >
              <Text style={styles.outlineButtonText}>Réinitialiser</Text>
            </TouchableOpacity>
          </View>
          <TouchableOpacity
            style={styles.upArrowButton}
            onPress={() => setShowSearch(false)}
            accessible={true}
            accessibilityLabel="Masquer la zone de recherche"
          >
            <FontAwesome name="chevron-up" size={20} color={styles.upArrowButtonText.color} />
          </TouchableOpacity>
        </View>
      )}

      {/* Liste des transactions */}
      <FlatList
        data={filteredPurchases}
        keyExtractor={(item) => item.id.toString()}
        renderItem={renderItem}
        contentContainerStyle={styles.listContent}
        ListEmptyComponent={<Text style={styles.emptyText}>Aucune transaction trouvée.</Text>}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  brandPrimary: {
    color: colorsChart.white,
  },
  brandAccent: {
    color: colorsChart.red,
  },
  placeholder: {
    color: '#aaa',
  },
  container: {
    flex: 1,
    backgroundColor: colorsChart.white, // Change background color to dark
    // paddingHorizontal: 16,
  },
  centered: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: colorsChart.white, // Change background color to dark
  },
  loadingText: {
    color: colorsChart.white, // Change text color to white for better contrast
    marginTop: 8,
    fontSize: 16,
  },
  errorText: {
    color: colorsChart.red,
    fontSize: 16,
  },
  searchContainer: {
    backgroundColor: colorsChart.primary, // Change background color to dark grey
    padding: 16,
    // borderRadius: 10,
    marginBottom: 16,
    // borderWidth:2
    // marginTop: 10,
  },
  inputGroup: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginTop: 12,
  },
  input: {
    flex: 1,
    backgroundColor: colorsChart.light, // Change background color to dark grey
    color: colorsChart.dark, // Change text color to white for better contrast
    borderRadius: 8,
    paddingHorizontal: 12,
    paddingVertical: 10,
    marginHorizontal: 4,
    fontSize: 14,
  },
  dateInput: {
    flex: 1,
    backgroundColor: colorsChart.light, // Change background color to dark grey
    borderRadius: 8,
    paddingHorizontal: 12,
    paddingVertical: 10,
    marginHorizontal: 4,
    justifyContent: 'center',
  },
  dateText: {
     // Change text color to white for better contrast
      fontSize: 14,
      },
      buttonRow: {
      flexDirection: 'row',
      justifyContent: 'space-between',
      marginTop: 12,
      },
      button: {
      backgroundColor: colorsChart.red,
      flex: 1,
      paddingVertical: 12,
      borderRadius: 8,
      alignItems: 'center',
      marginHorizontal: 4,
      shadowColor: '#000',
      shadowOffset: { width: 0, height: 2 },
      shadowOpacity: 0.25,
      shadowRadius: 3.84,
      elevation: 5, // For Android shadow
      },
      resetButton: {
      backgroundColor: colorsChart.primary,
      borderWidth: 2,
      borderColor:colorsChart.light
      },
      buttonText: {
      color: colorsChart.white,
      fontSize: 14,
      fontWeight: '700',
      },
      listContent: {
      paddingBottom: 20,
      },
     
      value: {
      color: colorsChart.white, // Change text color to white for better contrast
    fontSize: 14,
  },
  emptyText: {
    color: colorsChart.white, // Change text color to white for better contrast
    textAlign: 'center',
    marginTop: 20,
    fontSize: 16,
  },
  searchToggleButton: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: 'transparent',
    borderBottomColor: colorsChart.primary,
    borderBottomWidth: 1,
    paddingVertical: 5,
    paddingHorizontal: 10,
    borderRadius: 0,
    marginBottom: 16,
  },
  searchToggleButtonText: {
    color: colorsChart.primary,
    fontSize: 16,
    fontWeight: '700',
  },

  outlineButton: {
    backgroundColor: 'transparent',
    borderColor: colorsChart.red,
    borderWidth: 1,
  },
  outlineButtonText: {
    color: colorsChart.red,
    fontSize: 16,
    fontWeight: '700',
  },
  upArrowButton: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: 'transparent',
    borderColor: colorsChart.white,
    borderWidth: 1,
    paddingVertical: 5,
    paddingHorizontal: 10,
    borderRadius: 8,
    alignSelf: 'center',
    marginTop: 16,
  },
  upArrowButtonText: {
    color: colorsChart.white,
    fontSize: 16,
    fontWeight: '700',
  },
});

export default AchatVenteScreen;
