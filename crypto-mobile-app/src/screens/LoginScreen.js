import { View, Text, TextInput, ActivityIndicator, Button } from 'react-native'
import React, { useContext, useState } from 'react'
import { StyleSheet } from 'react-native'
import  * as LoginService from '../services/loginService';
import { AppContext } from '../../AppContext';

const LoginScreen = () => {
  const [email,setEmail] = useState("rocruxappafra-4143@yopmail.com");
  const [password, setPassword] = useState('mypassword');
  const [loading, setLoading] = useState(false);

  const {logIn} = useContext(AppContext)
  const signIn = async() => {
    setLoading(true)
    try {
      const userLog = await LoginService.logInWithMailAndPassword(email,password)
      logIn(userLog)
    }
    catch (error){
      console.log(error);
      var errorMessage = error.message;
      alert('Sign In failed :'+ errorMessage);
    }
    finally {
      setLoading(false);
    }
  }

  return (
    <View style={styles.container}>
      <TextInput style={styles.input} value={email} placeholder='Email' autoCapitalize='none' onChangeText={(text) => setEmail(text)}></TextInput>
      <TextInput style={styles.input} value={password} placeholder='Password' autoCapitalize='none' onChangeText={(text) => setPassword(text)} secureTextEntry={true}></TextInput>

      {loading ? (<ActivityIndicator size="large" color="#0000ff"/>
        ) : (
        <View style={styles.btnContainer}>
          <Button title='Login' style={styles.actBtn} onPress={signIn}/>        
        </View>
      )}

    </View>
  )
}

const styles = StyleSheet.create({
  container:{
    marginHorizontal : 20,
    flex: 1,
    justifyContent: 'center',
  },
  input:{
    marginVertical: 4,
    height: 50,
    borderRadius: 4,
    borderWidth:1,
    padding: 10,
    backgroundColor: '#fff' 
  },
  actBtn : {
    color: '#0000',
    backgroundColor:'#fff',
    flexGrow:1
  },
  btnContainer:{
    flexDirection:'row',
    gap:10
  }

});

export default LoginScreen;  