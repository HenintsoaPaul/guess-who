import { View, Text, TextInput, ActivityIndicator, Button } from 'react-native'
import React, { useState } from 'react'
import { StyleSheet } from 'react-native'
import { FIREBASE_AUTH } from '../services/firebaseService';
import { signInWithEmailAndPassword } from 'firebase/auth';
import { createUserWithEmailAndPassword } from 'firebase/auth';

const LoginScreen = () => {
  const [email,setEmail] = useState('chris@gmail.com');
  const [password, setPassword] = useState('chris123');
  const [loading, setLoading] = useState(false);
  const signIn = async() => {
    setLoading(true)
    try {
      const response = await signInWithEmailAndPassword(auth,email,password);
      console.log(response);
      alert('Log in');
    }
    catch (error){
      console.log(error);
      var errorMessage = error.message;
      if (error.contains("auth/network-request-failed")) {
        errorMessage = "verifiez votre connexion internet"
      }
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
          <Button title='Create account' style={styles.actBtn} onPress={signUp}/>        
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