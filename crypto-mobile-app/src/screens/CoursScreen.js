import React, { useState, useCallback, memo, useEffect, useContext } from 'react';
import { View, Text, StyleSheet, TextInput, TouchableOpacity, FlatList, ActivityIndicator } from 'react-native';
import FavoriteButton from '../components/atoms/FavoriteButton';
import {onSnapshot , collection, getDocs, query} from 'firebase/firestore';
import {FIRESTORE_DB } from '../services/firebaseService';
import { AppContext } from '../../AppContext';
import CryptoCard from '../components/molecules/CryptoCard';
import { colorsChart } from '../constants/ColorsChart';
import { LinearGradient } from "expo-linear-gradient";


const fetchCryptoData = async (setCryptos,setLoading) => {
  try {
    const cryptoRef = collection(FIRESTORE_DB, "crypto");
    const querySnapshot = await getDocs(cryptoRef);
    const cryptos = [];    
    querySnapshot.forEach((doc) => {
      cryptos.push(doc.data());
    });
    
    // setCryptos(cryptos);
    const unsubscribe = onSnapshot(cryptoRef,(snapshot) => {
      const updatedCryptos = [];
      snapshot.forEach((doc) => {
        updatedCryptos.push(doc.data());
      });
      setCryptos(updatedCryptos);
      console.log('Données mises à jour:', updatedCryptos);
    });
    return unsubscribe;
  } catch (error) {
    console.error('Erreur:', error);
    throw error;
  }
};

const CoursScreen = () => {
  const [cryptos, setCryptos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [filterText, setFilterText] = useState('');

  useEffect(() => {
    setLoading(true);
    const fetchCryptos = async () => {
      try {
        const unsubscribe = await fetchCryptoData(setCryptos,setLoading);
        return () => {
          if (unsubscribe) unsubscribe();
        };
      } catch (error) {
        console.error('Erreur lors du chargement des cryptomonnaies:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchCryptos();
  }, []);

  
  if (loading) {
    return (
      <View style={[styles.container,{
        justifyContent:'center',
        alignItems:'center'
      }]}>
        <ActivityIndicator size="large" color="#000" />
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <View style={styles.fundContainer}>
            <Text style={styles.fundLabel}>Solde total :</Text>
            {/* <Text style={styles.fundAmount}>{user.fund || 0} €</Text> */}
        </View>

      {/* Barre de recherche */}
        <View style={styles.searchContainer}>
            <TextInput
                style={styles.searchInput}
                placeholder="Rechercher une cryptomonnaie..."
                value={filterText}
                onChangeText={(text) => setFilterText(text)}
                />
        </View>

        <LinearGradient
                colors={["rgba(58,65,110,255)", "rgba(25,28,48,255)"]}
                style={[styles.headerOverlay]}
                >
            
            <FlatList
                data={cryptos.filter((crypto) =>
                    crypto.name.toLowerCase().includes(filterText.toLowerCase()) ||
                    crypto.symbol.toLowerCase().includes(filterText.toLowerCase())
                )}
                scrollEnabled={true}
                keyExtractor={(item) => item.id}
                style={{
                    flexDirection:'column',
                    flex:1,
                }}
                renderItem={({ item, index }) => (
                    <CryptoCard key={index} crypto={item}></CryptoCard>
                )}
            />
        </LinearGradient>

    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'flex-start',
    // alignItems: 'center',
    backgroundColor:colorsChart.light,
  },
  header: {
    width: '100%',
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginBottom: 16,
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
  },
  fundContainer: {
    width: '100%',
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingHorizontal:10,
    paddingVertical:8,
    backgroundColor:colorsChart.white,
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
    backgroundColor:colorsChart.white,
    paddingVertical:8,
    paddingHorizontal:10,
  },
  headerOverlay: {
    padding:10,
    flex:1
  },
  searchInput: {
    width: '100%',
    padding: 12,
    backgroundColor: '#f5f5f5',
    borderRadius: 4,
    borderWidth: 1,
    borderColor: '#ddd',
  }
});

export default CoursScreen;
