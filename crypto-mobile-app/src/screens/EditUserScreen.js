import { StyleSheet, View, Text } from 'react-native';
import React, { useContext, useState } from 'react';
import { AppContext } from '../../AppContext';
import { colorsChart } from '../constants/ColorsChart';
import InputGroup from '../components/atoms/InputGroup';
import { Button } from 'react-native-elements';
import {  updateOrCreateMobDoc } from '../services/firebaseService';
import EditPictureView from '../components/organisms/EditPictureView';

const EditUserScreen = () => {
    const {user,refreshUser} = useContext(AppContext)
    const [pseudo , setPseudo] = useState(user.pseudo)
    const [password , setPassword] = useState(user.password)
    const [loading,setLoading] = useState(false)

    if(user === null) {
      return <></>
    }
    
    const updateUser = async () => {
      setLoading(true)
      try {
        updateOrCreateMobDoc("account",user,{
          pseudo:pseudo,
          password:password
        });
        await refreshUser();
      } catch (error) {
        alert('Erreur de la modification : ', error.message);
      }
      finally {
        setLoading(false)
      }
    };
  return (
    <View style={styles.container}>
      <View style={styles.titleContainer}>
          <Text style={styles.title}>Editer photo de profil</Text>
      </View>
      <View style={styles.profilcontainer} >
        <EditPictureView />
      </View>
      <View style={styles.titleContainer}>
          <Text style={styles.title}>Editer information profil</Text>
      </View>
      <View style={styles.uinfoinputs}>
        <InputGroup 
            inputValue={pseudo} 
            setInputValue={setPseudo}
            placeholder={'Pseudo'}
            autoCapitalize={'none'}
        />
        <InputGroup 
            inputValue={password} 
            setInputValue={setPassword}
            placeholder={'Password'}
            autoCapitalize={'none'}
        />
      </View>
      <View style={styles.buttoncontainer}>
        <Button
            buttonStyle={styles.button}
            titleStyle={{color:colorsChart.primary}}
            title={'Modifer'}
            type='outline'
            loading={loading}
            onPress={updateUser}
        />
      </View>
    </View>
  )
}


export default EditUserScreen

const styles = StyleSheet.create({
    container:{
        flex:1,
        padding:10
    },
    titleContainer:{
      
      borderBottomWidth:1,
      flexDirection:'row',
      alignItems:'baseline',
      gap:10
    },
    profilcontainer:{
        alignItems:'center'
    }
    ,buttoncontainer:{
    },
    button: {
      // backgroundColor: colorsChart.primary,
      borderRadius: 12,
      borderColor:colorsChart.primary,
      height: 50,
      justifyContent: "center",
      alignItems: "center",
      marginVertical: 10,
    },
    title : {
      fontSize:24,
      color:colorsChart.dark,
      // borderTopWidth:1,
    } ,
    uinfoinputs: {

    }
});