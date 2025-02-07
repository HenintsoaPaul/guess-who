import React, { useEffect, useState, useContext } from 'react';
import { View, Text, StyleSheet, TouchableOpacity, Alert, Image } from 'react-native';
import * as ImagePicker from 'expo-image-picker';
import axios from 'axios';
import FormData from 'form-data';
import CryptoJS from 'crypto-js';
import { AppContext } from '../../../AppContext';
import { FontAwesome } from '@expo/vector-icons';
import { doc ,updateDoc} from 'firebase/firestore';
import { FIRESTORE_DB } from '../../services/firebaseService';

const CLOUDINARY_CONFIG = {
  cloudName: 'dulx9capq',
  apiKey: '174986489287854',
  apiSecret: 'k7dnDcMEbe24SF1jNB3YSPM1krA'
};

const EditableProfilePicture = ({editable=false}) => {
  const { image, setImage, user ,refreshUser} = useContext(AppContext);
  const [uploading, setUploading] = useState(false);
  const [loading,setLoading] = useState(false)
  const updateUser = async (imageUrl) => {
      setLoading(true)
      try {
        const accountRef = doc(FIRESTORE_DB, "account", user.id+"");  
        await updateDoc(accountRef, {
          profilImg : imageUrl
        });
        await refreshUser();
      } catch (error) {
        console.error('Erreur lors de la mise à jour de la liste des favoris dans Firestore:', error);
      }
      finally {
        setLoading(false)
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

  const uploadImage = async (imageAsset) => {
    setUploading(true);
    try {
      const formData = new FormData();
      formData.append('file', {
        uri: imageAsset.uri,
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

      console.log('Image uploadée avec succès:', response.data);
      return response.data.secure_url;
    } catch (error) {
      console.error('Erreur d\'upload:', error);
      Alert.alert('Erreur', 'Impossible d\'uploader l\'image');
      return null;
    } finally {
      setUploading(false);
    }
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
        const imageUrl = await uploadImage(result.assets[0]);
        if (imageUrl) {
          setImage(result.assets[0].uri);
          console.log('URL de l\'image:', imageUrl);
          updateUser(imageUrl)
        }
      } catch (error) {
        alert("Upload Failded ! :"+error.message)
      }
    }
  };

  return (
    <View style={styles.container}>
      <TouchableOpacity style={styles.imageContainer} onPress={pickImage} disabled={editable} >
        <Image
          source={image ? { uri: image } : require('../../../assets/profile.jpg')}
          style={styles.image}
        />
        <View style={styles.overlay}>
          <FontAwesome name='pencil' size={32} style={styles.overlaytext}/>
        </View>
      </TouchableOpacity>
      {uploading && <Text style={styles.uploadingText}>Upload en cours...</Text>}
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
