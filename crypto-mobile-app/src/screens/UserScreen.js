import { StyleSheet, ScrollView,View } from 'react-native'
import React, { useContext } from 'react'
import { Button } from 'react-native-elements';
import StyleText from '../components/atoms/StyleText';
import { colorsChart } from '../constants/ColorsChart';
import UserInfos from '../components/organisms/UserInfos';
import ProfilePicture from '../components/organisms/ProfilePicture';
import { useNavigation } from '@react-navigation/native';
import { AppContext } from '../../AppContext';
import UserButton from '../components/atoms/UserButton';
import { LinearGradient } from "expo-linear-gradient";


export default function UserScreen() {
  const {user,logOut} = useContext(AppContext);
  const navigation  = useNavigation();
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
          onPress={logOut}
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