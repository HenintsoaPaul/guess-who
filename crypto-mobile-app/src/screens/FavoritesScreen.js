import React, { useState, useEffect, useMemo, useContext } from 'react';
import { View, Text, StyleSheet, ActivityIndicator} from 'react-native';
import {  onSnapshot, query, where } from 'firebase/firestore';
import { AppContext } from '../../AppContext';
import { fetchDataFromFirebase, firebaseCollection } from '../services/firebaseService';
import NoFavoris from '../components/molecules/NoFavoris';
import CryptoCard from '../components/molecules/CryptoCard';
import { useNavigation } from '@react-navigation/native';
import CryptoFavCard from '../components/molecules/CryptoFavCard';
import { colorsChart } from '../constants/ColorsChart';

const unsubscribeFavorites = (setFavorites,user) => {
  
  const unsubscribe = onSnapshot(query(firebaseCollection('crypto_fav'),where('account.id',"==",user.id)),(snapshot) => {
    const uptFav = [];
    snapshot.forEach((doc) => {
      if(doc.data().onFav){
        uptFav.push(doc.data());
      }
    });
    setFavorites(uptFav);  });
  return unsubscribe;
}

const FavoritesScreen = () => {
  const [favorites, setFavorites] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [filterText, setFilterText] = useState('');
  const {user} = useContext(AppContext);
  const navigation = useNavigation(); 

  if(user === null) {
    return <></>
  }

  useEffect(() => {
    const fetchFavoris = async (user) => {
      setLoading(true);
      try {
        const data = await fetchDataFromFirebase(
          "crypto_fav",
          (cll) => {
            return query(cll,where("account.id","==",user.id),where("onFav",'==',true));
          }
        );        
        setFavorites(data);
        const unsubscribe = unsubscribeFavorites(setFavorites,user);
        return () => {
          unsubscribe();
        };
      } catch (error) {
        setError(error.message);
      } finally {
        setLoading(false);
      }
    };
    fetchFavoris(user);
  }, []);

  const filteredFavoris = useMemo(
    () =>
      favorites.filter(
        (favorite) =>
          favorite.crypto.name.toLowerCase().includes(filterText.toLowerCase()) ||
          favorite.crypto.symbol.toLowerCase().includes(filterText.toLowerCase())
      ),
    [favorites, filterText]
  );

  if (loading) {
    return (
      <View style={styles.container}>
        <ActivityIndicator size="large" color="#000" />
        <Text style={styles.loadingText}>Chargement des favoris...</Text>
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

  if( favorites === null||favorites.length === 0){
    return (
      <View style={styles.container}>
        <NoFavoris></NoFavoris>
      </View>
    );
  }
  return (
    <View style={styles.container}>
      {filteredFavoris.map((favorite, index) => (
        <CryptoFavCard key={index} crypto={favorite.crypto} style={{borderWidth:3,borderColor:colorsChart.gray}}></CryptoFavCard>
      ))
      }
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
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

export default FavoritesScreen;
