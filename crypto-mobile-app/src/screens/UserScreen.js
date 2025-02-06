import { StyleSheet, ScrollView,View } from 'react-native'
import React, { useContext } from 'react'
import { Button } from 'react-native-elements';
import StyleText from '../components/atoms/StyleText';
import { colors } from '../constants/Colors';
import UserInfos from '../components/organisms/UserInfos';
import ProfilePicture from '../components/organisms/ProfilePicture';
import { useNavigation } from '@react-navigation/native';
import { AppContext } from '../../AppContext';

export default function UserScreen() {
  const {user,logOut} = useContext(AppContext);
  const navigation  = useNavigation();
  return (
    < >
      <ScrollView style={styles.container}>
        <View style={styles.profileContainer}>
          <ProfilePicture></ProfilePicture>    
        </View>
        <StyleText style={styles.title} color={colors.white} fw={'bold'} fs={24}>{user.pseudo}</StyleText>
        <UserInfos></UserInfos>
        <View style={styles.buttonContainer}>
          <Button 
            title='' 
            onPress={()=> navigation.navigate('Portefeuille')}
            icon={{ name: 'money', type: 'font-awesome' }}
          />
          <Button 
            title=''
            type='solid'
            onPress={()=> navigation.navigate('Favoris')}
            icon={{ name: 'heart', type: 'font-awesome'}}
          />
          <Button 
            title='' 
            onPress={()=> navigation.navigate('Crypto cours')}
            icon={{ name: 'line-chart', type: 'font-awesome' }}
          />
        </View>
        <View style={{flex:1,alignItems:'center'}}>
          <Button 
            title='Se deconnecter'
            onPress={logOut}
            type='outline'
            buttonStyle={styles.signOut}
            titleStyle={{ color:colors.white}}
            />
        </View>
        </ScrollView>
    </>
  )
}

const styles = StyleSheet.create({
  profileContainer:{
    flex:1,
    alignItems:'center',
    justifyContent:'center',
  },
  container:{
    backgroundColor:colors.primary,
    paddingHorizontal:10,
    flex:1
  },
  title :
  {
    // borderTopWidth:1,
    borderBottomWidth:2,
    paddingBottom:10,
    borderColor:colors.white,
    marginVertical:10,
    textAlign:'center'
  },
  signOut:{
    marginTop:10,
    borderRadius:4,
    width:200,
  },
  buttonContainer : {
    padding:10,
    flex:1,
    flexDirection:'row',
    justifyContent:'space-evenly'
  },
});