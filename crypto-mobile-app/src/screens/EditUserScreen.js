import { StyleSheet, View, Text } from 'react-native';
import React, { useContext, useState } from 'react';
import { AppContext } from '../../AppContext';
import { colorsChart } from '../constants/ColorsChart';
import InputGroup from '../components/atoms/InputGroup';
import { Button } from 'react-native-elements';
import EditableProfilePicture from '../components/organisms/EditableProfilePicture';
import { doc, updateDoc } from 'firebase/firestore';
import { FIRESTORE_DB } from '../services/firebaseService';

const EditUserScreen = () => {
    const {user,refreshUser} = useContext(AppContext)
    const [pseudo , setPseudo] = useState(user.pseudo)
    const [password , setPassword] = useState(user.password)
    const [loading,setLoading] = useState(false)
    const updateUser = async () => {
      setLoading(true)
      try {
        const accountRef = doc(FIRESTORE_DB, "account", user.id+"");  
        await updateDoc(accountRef, {
          pseudo : pseudo
        });
        await refreshUser();
      } catch (error) {
        console.error('Erreur lors de la mise à jour de la liste des favoris dans Firestore:', error);
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
        <EditableProfilePicture />
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
            title={'Editer'}
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