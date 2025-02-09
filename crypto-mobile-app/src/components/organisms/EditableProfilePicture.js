import React, { useEffect, useState, useContext } from 'react';
import { View, Text, StyleSheet, TouchableOpacity, Alert, Image, ActivityIndicator } from 'react-native';
import * as ImagePicker from 'expo-image-picker';
import axios from 'axios';
import FormData from 'form-data';
import CryptoJS from 'crypto-js';
import { AppContext } from '../../../AppContext';
import { FontAwesome } from '@expo/vector-icons';
import { collection, doc ,getDoc,query,updateDoc, where} from 'firebase/firestore';
import { cloneMobData, fetchDataFromFirebase, FIRESTORE_DB } from '../../services/firebaseService';
import StyleText from '../atoms/StyleText';

const CLOUDINARY_CONFIG = {
  cloudName: 'dulx9capq',
  apiKey: '174986489287854',
  apiSecret: 'k7dnDcMEbe24SF1jNB3YSPM1krA'
};

const EditableProfilePicture = ({image,setImage,uploading=false}) => {
  useEffect(() => {
    requestPermissions();
  }, []);

  const requestPermissions = async () => {
    await ImagePicker.requestCameraPermissionsAsync();
    await ImagePicker.requestMediaLibraryPermissionsAsync();
  };

  const pickImage = () => {
    Alert.alert(
      'CHOISIR',
      'Sélectionnez une option pour ajouter une photo de profil',
      [
        { text: 'Galerie', onPress: pickImageFromGallery },
        { text: 'Appareil photo', onPress: pickImageFromCamera },
        { text: 'Annuler', style: 'cancel' },
      ],
      { cancelable: true }
    );
  };

  const pickImageFromGallery = async () => {
    const result = await ImagePicker.launchImageLibraryAsync({
      mediaTypes: ImagePicker.MediaTypeOptions.Images,
      allowsEditing: true,
      aspect: [1, 1],
      quality: 1,
    });
    handleImagePick(result);
  };

  const pickImageFromCamera = async () => {
    const result = await ImagePicker.launchCameraAsync({
      allowsEditing: true,
      aspect: [1, 1],
      quality: 1,
    });
    handleImagePick(result);
  };

  const handleImagePick = async (result) => {
    if (!result.canceled) {
      try {
        const uri = result.assets[0].uri;
        setImage(uri)        
      } catch (error) {
        alert("Picking image Failded ! :"+error.message)
      }
    }
  };

  return (
    <View style={styles.container}>
      <StyleText fs={20} style={{marginBottom: 10}}> Profil Preview </StyleText>
      <TouchableOpacity style={styles.imageContainer} onPress={pickImage} >
        <Image
          source={image ? { uri: image } : require('../../../assets/profile.jpg')}
          style={styles.image}
        />
        {uploading == true ? (
          <View style={styles.overlay}>
          <ActivityIndicator color={'#111'} size={'large'} />
        </View>
        ) : (<></>)}
        
        
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    position: 'relative',
    alignItems: 'center',
    justifyContent: 'center',
    width: 180,
    height: 180,
    gap:15
  },
  imageContainer: {
    width: 160,
    height: 160,
    borderRadius: 80,
    justifyContent:'center',
    alignItems:'center',
    overflow: 'hidden',
    borderWidth: 2,
    borderColor: '#ddd',
  },
  image: {
    width: 160,
    height: 160,
    borderRadius: 80,
  },
  uploadingText: {
    marginTop: 8,
    color: '#666',
    textAlign: 'center',
  },
  overlay:{
    position:'absolute',
    backgroundColor:'rgba(255,255,255,0.5)',
    top:0,
    bottom:0,
    left:0,
    right:0,
    alignItems:'center',
    justifyContent:'center',
  },
  overlaytext : {
    color:'rgba(0,0,0,0.8)',
    
  }
  
});

export default EditableProfilePicture;
