import { StyleSheet, ScrollView,Button,View } from 'react-native'
import React from 'react'
import { SafeAreaView } from 'react-native-safe-area-context';
import StyleText from '../components/atoms/StyleText';
import { colors } from '../constants/Colors';
import UserInfos from '../components/organisms/UserInfos';
import { getUserCryptoFavoris } from '../services/cryptoService';
import { FIREBASE_AUTH } from '../services/firebaseService';
import ProfilePicture from '../components/organisms/ProfilePicture';
import { useNavigation } from '@react-navigation/native';

export default function UserScreen() {
  const navigation  = useNavigation();
  const favoris = getUserCryptoFavoris();
  return (
    <SafeAreaView style={styles.container}>
      <ScrollView>
        <View style={styles.profileContainer}>
          <ProfilePicture></ProfilePicture>    
        </View>
        <StyleText style={styles.title} color={colors.primary} fw={'bold'} fs={24}> Account </StyleText>
        <UserInfos></UserInfos>
        <View style={styles.buttonContainer}>
          <Button style={styles.userButton} title='Consulter Wallet' onPress={()=> navigation.navigate('Portefeuille')}/>
          <Button style={styles.userButton} title='Voir Les favoris' onPress={()=> navigation.navigate('Favoris')}/>
        </View>
        <View style={{marginTop:10}}>
          <Button 
            title='Se deconnecter'
            onPress={()=> FIREBASE_AUTH.signOut()}
            style={styles.signOut} 
          />
        </View>

      </ScrollView>
    </SafeAreaView>
  )
}

const styles = StyleSheet.create({
  profileContainer:{
    flex:1,
    alignItems:'center'
  },
  container:{
    paddingHorizontal:10
  },
  title :
  {
    // borderTopWidth:1,
    // borderBottomWidth:1,
    marginVertical:10,
    // textAlign:'center'
  },
  signOut:{
    backgroundColor:colors.secondary,
    marginTop:10,
  },
  buttonContainer : {
    flex:1,
    flexDirection:'row',
    alignItems:'center',
    justifyContent:'center'
  },
  userButton:{
    borderWidth:1,
    padding:10,
    backgroundColor:'#1c2e4a',
    color:'#fff',
    textTransform:'capitalize'
  }
});