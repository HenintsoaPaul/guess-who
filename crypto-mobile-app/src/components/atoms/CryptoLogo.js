import { Image, StyleSheet, View } from 'react-native';
import React, { useState, useEffect } from 'react';
import { firebaseCollection } from '../../services/firebaseService';
import { onSnapshot, query, where } from 'firebase/firestore';

const getLogo = (crypto) => {
  if (crypto.logo) {
    return crypto.logo;
  }
  return "https://res.cloudinary.com/dulx9capq/image/upload/v1738869028/default_coin_na19vv.png";
};


const fetchLogo = (crypto,setLogo) => {

    const unsubscribe = onSnapshot(query(firebaseCollection('crypto'),where("id", '==', crypto.id)), (snapshot) => {
        const uptFav = []
        snapshot.forEach((doc) => {
            if (doc.data()) {
                uptFav.push(doc.data());
            }
        });
        console.log(uptFav);
    
        if (uptFav.length > 0 && uptFav[0].logo !== null) {
            setLogo(uptFav[0].logo)

        }
    });
    return unsubscribe;
}


const CryptoLogo = ({ crypto }) => {
  const [cryptoLogo, setCryptoLogo] = useState(getLogo(crypto));

  useEffect(() => {
    const unusub = async () => {
        try {
            const unsubscribe = fetchLogo(crypto,setCryptoLogo)
            return () => {
                if (unsubscribe) unsubscribe();
            };
        } catch (error) {
            alert(error.message)   
        }
    }
    unusub()
    setCryptoLogo(getLogo(crypto));
  }, [crypto]);
  return (
    <View style={styles.logoContainer}>
      <Image style={styles.logo} source={{ uri: cryptoLogo }} />
    </View>
  );
};

export default CryptoLogo;

const styles = StyleSheet.create({
  logo: {
    width: 40,
    height: 40,
    borderRadius: 100,
  },
  logoContainer: {
    paddingRight: 10,
  },
});