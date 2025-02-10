import React, { useEffect, useState, useContext } from 'react';
import { View,  StyleSheet, Alert } from 'react-native';
import * as ImagePicker from 'expo-image-picker';
import axios from 'axios';
import FormData from 'form-data';
import CryptoJS from 'crypto-js';
import { AppContext } from '../../AppContext';
import {updateOrCreateMobDoc } from '../services/firebaseService';
import { useNavigation } from '@react-navigation/native';
import EditableProfilePicture from '../components/organisms/EditableProfilePicture';
import { Button } from 'react-native-elements';
import { colorsChart } from '../constants/ColorsChart';

const CLOUDINARY_CONFIG = {
    cloudName: 'dulx9capq',
    apiKey: '174986489287854',
    apiSecret: 'k7dnDcMEbe24SF1jNB3YSPM1krA'
  };
const PictureEditScreen = () => {
    const { image, user ,refreshUser} = useContext(AppContext);
    const [editImage , setEditImage] = useState(image);
    const [uploading, setUploading] = useState(false);
    const navigation = useNavigation()
    if(user === null) {
      return <></>
    }
    const updateUser = async (imageUrl) => {
        try {
            setUploading(true)
            updateOrCreateMobDoc("account",user,{accountImg:imageUrl});
            await refreshUser();
        } catch (error) {
        console.error('Erreur lors de la mise à jour de la liste des favoris dans Firestore:', error);
        }
        finally {
            setUploading(false)
        }
    };
    useEffect(() => {
        requestPermissions();
    }, []);
    
    const requestPermissions = async () => {
    await ImagePicker.requestCameraPermissionsAsync();
    await ImagePicker.requestMediaLibraryPermissionsAsync();
    };
    
    const generateSignature = () => {
    const timestamp = Date.now().toString();
    const toSign = `timestamp=${timestamp}${CLOUDINARY_CONFIG.apiSecret}`;
    return CryptoJS.SHA1(toSign).toString();
    };

    const edit = async ()=> {
      try {
        const secureUrl = await uploadImage(editImage);
        await updateUser(secureUrl);
      } catch (error) {
        Alert.alert(error.message)
      }
    }

    const uploadImage = async (imageUri) => {
      setUploading(true);
      try {
        const formData = new FormData();
        formData.append('file', {
          uri: imageUri,
          type: 'image/jpeg',
          name: `profile-${user.id}.jpg`,
        });
        formData.append('api_key', CLOUDINARY_CONFIG.apiKey);
        formData.append('timestamp', Date.now().toString());
        formData.append('signature', generateSignature());
  
        const response = await axios.post(
          `https://api.cloudinary.com/v1_1/${CLOUDINARY_CONFIG.cloudName}/image/upload`,
          formData,
          { headers: { 'Content-Type': 'multipart/form-data' } }
        );
          return response.data.secure_url;
      } catch (error) {
        console.error('Erreur d\'upload:', error);
        throw new Error('Erreur', 'Impossible d\'uploader l\'image');
      } finally {
        setUploading(false);
      }
    };
  
    
  return (
    <View style={styles.container}>
      <EditableProfilePicture image={editImage} setImage={setEditImage} uploading={uploading} />
      <View style={styles.buttonContainer}>
        <Button buttonStyle={{
                backgroundColor:colorsChart.primary,
                width:150,
            }} 
            onPress={edit}
            title={'Modifier'} 
        />
        <Button buttonStyle={{
                borderColor: colorsChart.red,
                width:150
            }} 
            titleStyle={{
                color:colorsChart.red
            }}
            type='outline'
            onPress={()=> navigation.navigate('ProfileEdit')}
            title={'Annuler'} 
        />
        </View>
    </View>
  )
}

export default PictureEditScreen

const styles = StyleSheet.create({
    container:{
        flex:1,
        justifyContent:'center',
        alignItems:'center'
    },
    buttonContainer:{
        marginTop:40,
        flexDirection:'row',
        gap:10
    }
})