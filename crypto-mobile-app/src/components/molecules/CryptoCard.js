import { View, StyleSheet } from 'react-native'
import React, { useEffect, useState } from 'react'
import StyleText from '../atoms/StyleText';
import { colorsChart } from '../../constants/ColorsChart';
import { Image } from 'react-native-elements';
import { collection, limit, orderBy, query, where , onSnapshot, getDocs} from 'firebase/firestore';
import { FIRESTORE_DB } from '../../services/firebaseService';
import { ActivityIndicator } from 'react-native-web';

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
      console.log('Données mises à jour:', updatedCryptos);
    });
    return unsubscribe;
  } catch (error) {
    console.error('Erreur:', error);
    throw error;
  }
};

export default function CryptoCard({crypto}) {
  const [cours,setCours] = useState([]);
  const [loading,setLoading] = useState(true);  
  useEffect(()=>{
    try {
      const unsubscribe = fetchCoursData(setCours, crypto);
      return () => {
        if (unsubscribe) unsubscribe();
      };
    } catch (error) {
      console.error('Erreur lors du cours de la cryptomonnaie : '+crypto.name, error);
    } finally {
      setLoading(false);
    }
  },[]);

  return (
    <View style={styles.container}>
      <Image />
      <View style={styles.detail}>
        <StyleText fw={700} color={colorsChart.primary} >{crypto.name}</StyleText>
        <StyleText fs={12} color={colorsChart.dark}>{crypto.symbol}</StyleText>
      </View>
      <View >
        {
          cours.length == 0 ? 
          (
            <>
              <ActivityIndicator size="large" color="#000" />
            </>
          ) : 
          (
            <>
              <StyleText>{cours[0].pu} $</StyleText>
              <StyleText>+0.05</StyleText>
            </>
          )
        }
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
        marginBottom:5,
        borderRadius:4
    }
    ,detail :{
      flex:1,
      flexDirection:'column',
    }
});