import { View, StyleSheet, Text, ActivityIndicator } from 'react-native';
import React, { useContext, useEffect, useState } from 'react';
import StyleText from '../atoms/StyleText';
import { colorsChart } from '../../constants/ColorsChart';
import { collection, limit, orderBy, query, where, onSnapshot } from 'firebase/firestore';
import { AppContext } from '../../../AppContext';
import FavButton from '../atoms/FavButton';
import CryptoLogo from '../atoms/CryptoLogo';
import { getTimestampNow } from '../../services/timeService';
import { firebaseCollection, FIRESTORE_DB, updateOrCreateMobDoc } from '../../services/firebaseService';

const fetchCoursData = async (setCours, crypto) => {
  try {
    const coursCollectionRef = collection(FIRESTORE_DB, "cours");
    const querySnapshot = query(coursCollectionRef, where('id', '==', crypto.id), orderBy("dateCours", "desc"), limit(2));

    const unsubscribe = onSnapshot(querySnapshot, (snapshot) => {
      const updatedCryptos = [];
      snapshot.forEach((doc) => {
        updatedCryptos.push(doc.data());
      });
      setCours(updatedCryptos);
    });
    return unsubscribe;
  } catch (error) {
    throw error;
  }
};

const unsubscribeFavorite = (setFavorite, setOnFav, user, crypto) => {
  const unsubscribe = onSnapshot(query(firebaseCollection('crypto_fav'),
    where("account.id", "==", user.id), where("crypto.id", '==', crypto.id)), (snapshot) => {
    const uptFav = [];
    snapshot.forEach((doc) => {
      if (doc.data().onFav) {
        uptFav.push(doc.data());
      }
    });
    if (uptFav.length > 0) {
      setFavorite(uptFav[0]);
      setOnFav(uptFav[0].onFav);
    } else {
      setFavorite(null);
      setOnFav(false);
    }
  });
  return unsubscribe;
}

export default function CryptoFavCard({ crypto ,style}) {
  const [cours, setCours] = useState([]);
  const [loading, setLoading] = useState(true);
  const [favorite, setFavorite] = useState(null);
  const [onFav, setOnFav] = useState(false);
  const { user } = useContext(AppContext);

  useEffect(() => {
    async function unsubData() {
      try {
        const unsubscribe = await fetchCoursData(setCours, crypto);
        return () => {
          if (unsubscribe) unsubscribe();
        };
      } catch (error) {
        console.error('Erreur lors du cours de la cryptomonnaie : ' + crypto.name, error);
      } finally {
        setLoading(false);
      }
    }
    const unsubFav = async () => {
      setLoading(true);
      try {
        const unsubscribe = unsubscribeFavorite(setFavorite, setOnFav, user, crypto);
        return () => {
          if (unsubscribe) unsubscribe();
        };
      } catch (error) {
        alert(error.message);
      } finally {
        setLoading(false);
      }
    };

    unsubFav();
    unsubData();
  }, []);

  const updateFav = () => {
    if (favorite === null) {
      const favData = {
        id: null,
        account: { id: user.id },
        onFav: true,
        dateCryptoFav: getTimestampNow(),
        crypto: { id: crypto.id }
      }
      updateOrCreateMobDoc("crypto_fav", favData, {})
    } else {
      const favData = {
        onFav: !favorite.onFav,
        dateCryptoFav: getTimestampNow(),
      }
      updateOrCreateMobDoc("crypto_fav", favorite, favData)
    }
    setOnFav(!onFav)
  }

  return (
    <View style={[styles.container,style]}>
      <CryptoLogo crypto={crypto}></CryptoLogo>
      <View style={styles.detail}>
        <StyleText fw={700} color={colorsChart.primary} >{crypto.name}</StyleText>
        <StyleText fs={12} color={colorsChart.dark}>{crypto.symbol}</StyleText>
      </View>
      <View style={{ flexDirection: 'row' }}>
        {
          cours.length == 0 ?
            (
              <>
                <ActivityIndicator size="large" color="#000" />
              </>
            ) :
            (
              <View style={{ flexDirection: 'column', justifyContent: 'center', }}>
                <StyleText>{cours[0].pu} $</StyleText>
                <StyleText>+0.05</StyleText>
              </View>
            )
        }

        <FavButton style={styles.favContainer} onPress={updateFav} onFav={onFav}></FavButton>

      </View>
    </View>
  )
}

const styles = StyleSheet.create({
  container: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    backgroundColor: colorsChart.white,
    alignItems: 'center',
    padding: 10,
    borderRadius: 4
  },
  detail: {
    flex: 1,
    flexDirection: 'column',
  },
  favContainer: {
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: colorsChart.white,
    justifyContent: 'center',
    paddingLeft: 10,
    marginLeft: 10,
    borderLeftWidth: 3,
    borderColor: colorsChart.primary
  }
});