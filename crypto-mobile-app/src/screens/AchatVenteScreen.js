import React, { useState, useEffect } from 'react';
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
import { getDocs, collection } from 'firebase/firestore';
import { fetchDataFromFirebase, FIRESTORE_DB } from '../services/firebaseService';
import { FontAwesome } from '@expo/vector-icons'; // Import FontAwesome for the search icon
import DateTimePicker from '@react-native-community/datetimepicker'; // Import DateTimePicker
import { colorsChart } from '../constants/ColorsChart';

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

  useEffect(() => {
    const fetchPurchases = async () => {
      try {
        const purchasesData = fetchDataFromFirebase('purchase',null)
        setPurchases(purchasesData);
        setFilteredPurchases(purchasesData);
      } catch (err) {
        setError('Erreur lors de la récupération des données');
        console.error('Erreur:', err);
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
        <ActivityIndicator size="large" color={styles.brandAccent.color} />
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
    const cryptoName = item.saleDetailDocument?.crypto?.name || item.crypto?.name || 'N/A';
    return (
      <View style={styles.card} accessible={true} accessibilityLabel={`Transaction ${item.id}`}>
        <View style={styles.row}>
          <View style={styles.col}>
            <Text style={styles.label}>Acheteur :</Text>
            <Text style={styles.value}>{item.accountPurchaser?.pseudo || 'N/A'}</Text>
          </View>
          <View style={styles.col}>
            <Text style={styles.label}>Vendeur :</Text>
            <Text style={styles.value}>{item.accountSeller?.pseudo || 'N/A'}</Text>
          </View>
        </View>
        <View style={styles.row}>
          <View style={styles.col}>
            <Text style={styles.label}>Crypto :</Text>
            <Text style={styles.value}>{cryptoName}</Text>
          </View>
          <View style={styles.col}>
            <Text style={styles.label}>Quantité :</Text>
            <Text style={styles.value}>{item.quantityCrypto}</Text>
          </View>
        </View>
        <View style={styles.row}>
          <View style={styles.col}>
            <Text style={styles.label}>Prix Total :</Text>
            <Text style={styles.value}>{item.totalPrice} €</Text>
          </View>
          <View style={styles.col}>
            <Text style={styles.label}>Date :</Text>
            <Text style={styles.value}>
              {item.datePurchase ? new Date(item.datePurchase.seconds * 1000).toLocaleDateString() : 'N/A'}
            </Text>
          </View>
        </View>
      </View>
    );
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
              style={[styles.button, styles.outlineButton]}
              onPress={handleSearch}
              accessible={true}
              accessibilityLabel="Rechercher"
            >
              <Text style={[styles.buttonText, styles.outlineButtonText]}>Rechercher</Text>
            </TouchableOpacity>
            <TouchableOpacity
              style={[styles.button, styles.resetButton]}
              onPress={handleReset}
              accessible={true}
              accessibilityLabel="Réinitialiser"
            >
              <Text style={styles.buttonText}>Réinitialiser</Text>
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
    color: '#ffffff',
  },
  brandAccent: {
    color: '#00b894',
  },
  placeholder: {
    color: '#aaa',
  },
  container: {
    flex: 1,
    backgroundColor: colorsChart.white, // Change background color to dark
    paddingHorizontal: 16,
  },
  centered: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: colorsChart.white, // Change background color to dark
  },
  loadingText: {
    color: '#ffffff', // Change text color to white for better contrast
    marginTop: 8,
    fontSize: 16,
  },
  errorText: {
    color: '#ff5555',
    fontSize: 16,
  },
  searchContainer: {
    backgroundColor: '#1e1e1e', // Change background color to dark grey
    padding: 16,
    borderRadius: 10,
    marginBottom: 16,
    marginTop: 10,
  },
  searchTitle: {
    color: '#ffffff', // Change text color to white for better contrast
    fontSize: 18,
    fontWeight: '700',
    marginBottom: 12,
    textAlign: 'center',
  },
  inputGroup: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginTop: 12,
  },
  input: {
    flex: 1,
    backgroundColor: '#2a2a2a', // Change background color to dark grey
    color: '#ffffff', // Change text color to white for better contrast
    borderRadius: 8,
    paddingHorizontal: 12,
    paddingVertical: 10,
    marginHorizontal: 4,
    fontSize: 14,
  },
  dateInput: {
    flex: 1,
    backgroundColor: '#2a2a2a', // Change background color to dark grey
    borderRadius: 8,
    paddingHorizontal: 12,
    paddingVertical: 10,
    marginHorizontal: 4,
    justifyContent: 'center',
  },
  dateText: {
    color: '#ffffff', // Change text color to white for better contrast
    fontSize: 14,
  },
  buttonRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginTop: 12,
  },
  button: {
    backgroundColor: '#00b894',
    flex: 1,
    paddingVertical: 12,
    borderRadius: 8,
    alignItems: 'center',
    marginHorizontal: 4,
  },
  resetButton: {
    backgroundColor: '#0984e3',
  },
  buttonText: {
    color: '#ffffff',
    fontSize: 14,
    fontWeight: '700',
  },
  listContent: {
    paddingBottom: 20,
  },
  card: {
    backgroundColor: '#1e1e1e', // Change background color to dark grey
    padding: 16,
    borderRadius: 10,
    marginBottom: 12,
  },
  row: {
    flexDirection: 'row',
    marginBottom: 8,
  },
  col: {
    flex: 1,
    justifyContent: 'center',
  },
  label: {
    color: '#aaa',
    fontSize: 14,
    fontWeight: '600',
  },
  value: {
    color: '#ffffff', // Change text color to white for better contrast
    fontSize: 14,
  },
  emptyText: {
    color: '#ffffff', // Change text color to white for better contrast
    textAlign: 'center',
    marginTop: 20,
    fontSize: 16,
  },
  searchToggleButton: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: 'transparent',
    borderBottomColor: '#00b894',
    borderBottomWidth: 1,
    paddingVertical: 5,
    paddingHorizontal: 10,
    borderRadius: 0,
    marginBottom: 16,
  },
  searchToggleButtonText: {
    color: '#00b894',
    fontSize: 16,
    fontWeight: '700',
  },
  topSearchContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#2a2a2a', // Change background color to dark grey
    borderRadius: 8,
    paddingHorizontal: 12,
    paddingVertical: 10,
    marginBottom: 16,
  },
  topSearchInput: {
    flex: 1,
    color: '#ffffff', // Change text color to white for better contrast
    fontSize: 14,
  },
  topSearchButton: {
    marginLeft: 8,
  },
  outlineButton: {
    backgroundColor: 'transparent',
    borderColor: '#00b894',
    borderWidth: 1,
  },
  outlineButtonText: {
    color: '#00b894',
  },
  upArrowButton: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: 'transparent',
    borderColor: '#00b894',
    borderWidth: 1,
    paddingVertical: 5,
    paddingHorizontal: 10,
    borderRadius: 8,
    alignSelf: 'center',
    marginTop: 16,
  },
  upArrowButtonText: {
    color: '#00b894',
    fontSize: 16,
    fontWeight: '700',
  },
});

export default AchatVenteScreen;
