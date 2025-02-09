import { View,
     StyleSheet } from 'react-native'
import React, { useContext, useEffect, useState } from 'react'
import StyleText from '../atoms/StyleText';
import { colorsChart } from '../../constants/ColorsChart';
import { Image } from 'react-native-elements';
import { collection, limit, orderBy, query, where , onSnapshot, getDocs} from 'firebase/firestore';
import { fetchDataFromFirebase, firebaseCollection, FIRESTORE_DB, updateOrCreateMobDoc } from '../../services/firebaseService';
import { ActivityIndicator } from 'react-native';
import { FontAwesome } from '@expo/vector-icons';
import { AppContext } from '../../../AppContext';
import FavButton from '../atoms/FavButton';

const fetchCoursData = async (setCours,crypto) => {
  try {
    const coursCollectionRef = collection(FIRESTORE_DB, "cours");
    const querySnapshot = query(coursCollectionRef , where('id', '==', crypto.id), orderBy("dateCours", "desc"), limit(2));

    const unsubscribe = onSnapshot(querySnapshot,(snapshot) => {
      const updatedCryptos = [];
      snapshot.forEach((doc) => {
        updatedCryptos.push(doc.data());
      });
      setCours(updatedCryptos);
    });
    return unsubscribe;
  } catch (error) {
    console.error('Erreur:', error);
    throw error;
  }
};

const unsubscribeFavorite = (favorite,setFavorite,setOnFav,user,crypto) => {
  
  const unsubscribe = onSnapshot(query(firebaseCollection('crypto_fav'),
  where("account.id","==",user.id),where("crypto.id",'==',crypto.id)),(snapshot) => {
    const uptFav = [];
    snapshot.forEach((doc) => {
      if(doc.data().onFav){
        uptFav.push(doc.data());
      }
    });
    if (uptFav.length > 0) {
        setFavorite(uptFav[0]);
    }
    else {
        setFavorite(null);
    }
    
    if (favorite !== null) {
        setOnFav(favorite.onFav);
    }
  });
  return unsubscribe;
}

export default function CryptoFavCard({crypto}) {
  const [cours,setCours] = useState([]);
  const [loading,setLoading] = useState(true);
  const [favorite,setFavorite] = useState(null);
  const [onFav ,setOnFav] = useState(false);
  const {user} = useContext(AppContext)
  useEffect(()=>{
    async function fecthData(){
      try {
        const unsubscribe = await fetchCoursData(setCours, crypto);        
        return () => {
          if (unsubscribe) unsubscribe();
        };
      } catch (error) {
        console.error('Erreur lors du cours de la cryptomonnaie : '+crypto.name, error);
      } finally {
        setLoading(false);
      }
    } 
    const fetchFavoris = async () => {
        setLoading(true);
        try {        
        const data = await fetchDataFromFirebase(
            "crypto_fav",
            (cll) => {
            return query(cll,where("account.id","==",user.id),where("crypto.id",'==',crypto.id));
            }
        );
        if (data.length > 0) {
            setFavorite(data[0]);
        }
        else (
            setFavorite(null)
        )
        if (favorite !== null) {
            setOnFav(favorite.onFav);
        }
        const unsubscribe = unsubscribeFavorite(favorite,setFavorite,setOnFav,user,crypto);
        return () => {
            if(unsubscribe) unsubscribe();
        };
        } catch (error) {
            alert(error.message);
        } finally {
        setLoading(false);
        }
    };

    fecthData();
    fetchFavoris();
  },[]);


  const updateFav = () =>{
    if (favorite === null) {
        const favData = {
            id:null,
            account : {id:user.id},
            onFav: true,
            dateCryptoFav:new Date().toISOString(),
            crypto: {id:crypto.id}
        }
            updateOrCreateMobDoc("crypto_fav",favData,{})
    }
    else {
        const favData = {
            onFav: !favorite.onFav,
            dateCryptoFav:new Date().toISOString()
        }
            updateOrCreateMobDoc("crypto_fav",favorite,favData)
    }
    setOnFav(!onFav)
  }

  const favView = ()=>{
        return <FavButton style={styles.favContainer} onPress={updateFav} onFav={onFav}></FavButton>
  }

  return (
    <View style={styles.container}>
      <Image />
      <View style={styles.detail}>
        <StyleText fw={700} color={colorsChart.primary} >{crypto.name}</StyleText>
        <StyleText fs={12} color={colorsChart.dark}>{crypto.symbol}</StyleText>
      </View>
      <View style={{flexDirection:'row'}}>
        {
          cours.length == 0 ? 
          (
            <>
              <ActivityIndicator size="large" color="#000" />
            </>
          ) : 
          (
            <View style={{flexDirection:'column',justifyContent:'center',}}>
              <StyleText>{cours[0].pu} $</StyleText>
              <StyleText>+0.05</StyleText>
            </View>
          )
        }
        
        {favView()}
        
      </View>
    </View>
  )
}

const styles = StyleSheet.create({
    container:{
        flexDirection:'row',
        justifyContent:'space-between',
        backgroundColor:colorsChart.white,
        alignItems:'center',
        padding:10,
        borderRadius:4
    }
    ,detail :{
      flex:1,
      flexDirection:'column',
    }
    ,favContainer : {
        justifyContent:'center',
        alignItems:'center',
        backgroundColor:colorsChart.white,
        justifyContent:'center',
        paddingLeft:10,
        marginLeft:10,
        borderLeftWidth:3
        ,borderColor:colorsChart.primary
    }
});