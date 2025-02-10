import { StyleSheet, ScrollView,View } from 'react-native'
import React, { useContext, useEffect } from 'react'
import { Button } from 'react-native-elements';
import StyleText from '../components/atoms/StyleText';
import { colorsChart } from '../constants/ColorsChart';
import UserInfos from '../components/organisms/UserInfos';
import ProfilePicture from '../components/organisms/ProfilePicture';
import { useNavigation } from '@react-navigation/native';
import { AppContext } from '../../AppContext';
import UserButton from '../components/atoms/UserButton';
import { LinearGradient } from "expo-linear-gradient";
import { firebaseCollection} from '../services/firebaseService';
import { onSnapshot, query, where } from 'firebase/firestore';


const unsubscribeAccount = async(setUser,setImage,user) => {
  const unsubscribe = onSnapshot(query(firebaseCollection('account'),where('id',"==",user.id)),(snapshot) => {
    const uptAcc = [];
    snapshot.forEach((doc) => {
      if(doc.data()){
        uptAcc.push(doc.data());
      }
    });
    if (uptAcc.length > 0) {
      setUser(uptAcc[0]);
      setImage(uptAcc[0].accountImg)
    }
  });
  return unsubscribe;
}

export default function UserScreen() {
  const {user,logOut,setUser,setImage} = useContext(AppContext);
  const navigation  = useNavigation();
  const deconnecter = () => {
    logOut();
  }
  if (user === null) {
      return <></>
  }
  useEffect(() => {
    
    const fetchUser = async () => {
      const unsubscribe = await unsubscribeAccount(setUser,setImage,user);
      return () => {
        if (unsubscribe) unsubscribe();
      };
    }
    fetchUser();
   }, [])
  return (
    <View style={styles.container}>
      <View>
        <LinearGradient
          colors={["rgba(58,65,110,255)", "rgba(25,28,48,255)"]}
          style={styles.headerOverlay}
        >
        <ProfilePicture></ProfilePicture>    
        <StyleText style={styles.title} color={colorsChart.white} fw={'bold'} fs={24}>{user.pseudo}</StyleText>
        <StyleText style={styles.title} color={colorsChart.white} fw={'200'} fs={12}>{user.email}</StyleText>
        </LinearGradient>
      </View>
      <View style={{paddingVertical:25,flex:1}}>
        <UserInfos></UserInfos>
      </View>
      <View style={styles.buttonContainer}>
        <UserButton 
          iconName={'money'}
          onPress={()=> navigation.navigate('Portefeuille')}
        />
        <UserButton
          onPress={()=> navigation.navigate('Favoris')}
          iconName={'heart'}
        />
        <UserButton
          onPress={()=> navigation.navigate('Crypto cours')}
          iconName={'line-chart'}
        />
      </View>
      <View style={{}}>
        <Button 
          title='Se deconnecter'
          onPress={deconnecter}
          buttonStyle={styles.signOut}
          titleStyle={{ color:colorsChart.red}}
          />
      </View>
    </View>
  )
}

const styles = StyleSheet.create({
  container:{
    backgroundColor:colorsChart.white,
    flex:1
  },
  title :
  {
    borderColor:colorsChart.white,
    textAlign:'center'
  },
  headerOverlay: {
    justifyContent: "center",
    alignItems: "center",
    padding:10,
  },
  signOut:{
    borderColor:colorsChart.red,
    backgroundColor:'',
    borderWidth:1,
    borderRadius:100,
    marginBottom:25,
    marginHorizontal:10
  },
  buttonContainer : {
    marginHorizontal:10,
    padding:10,
    flex:1,
    flexDirection:'row',
    justifyContent:'space-between'
  },
});