import React, { useState,useEffect, useContext,} from 'react';
import { View, Text, StyleSheet, TextInput,FlatList, ActivityIndicator } from 'react-native';
import {onSnapshot , collection, getDocs} from 'firebase/firestore';
import {fetchDataFromFirebase, firebaseCollection, FIRESTORE_DB } from '../services/firebaseService';
import CryptoCard from '../components/molecules/CryptoCard';
import { colorsChart } from '../constants/ColorsChart';
import { LinearGradient } from "expo-linear-gradient";
import { FontAwesome } from '@expo/vector-icons';
import { AppContext } from '../../AppContext';
import CryptoFavCard from '../components/molecules/CryptoFavCard';


const fetchCryptoData = (setCryptos) => {
  try {
    const unsubscribe = onSnapshot(firebaseCollection('crypto'),(snapshot) => {
      const updatedCryptos = [];
      snapshot.forEach((doc) => {
        updatedCryptos.push(doc.data());
      });
      setCryptos(updatedCryptos);
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
  const {user} = useContext(AppContext);
  
  useEffect(() => {
    setLoading(true);
    const fetchCryptos = async () => {
      try {
        const unsubscribe = fetchCryptoData(setCryptos);
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

  if(user === null) {
    return <></>
  }
  
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
            <Text style={styles.fundAmount}>{user.fund || 0} €</Text>
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

        <View
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
                contentContainerStyle={{gap:8}}
                renderItem={({ item, index }) => (
                  <CryptoFavCard key={index} crypto={item} style={{borderWidth:3,borderColor:colorsChart.gray}}></CryptoFavCard>
                )}
            />
        </View>

    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'flex-start',
    backgroundColor:colorsChart.white,
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
