import React, { useEffect, useState } from 'react';
import { View, Text, StyleSheet, TouchableOpacity, Alert, Image } from 'react-native';
import * as ImagePicker from 'expo-image-picker';
import { useNavigation } from '@react-navigation/native';
import axios from 'axios';
import FormData from 'form-data';
import CryptoJS from 'crypto-js';

const ProfilePicture = () => {
  const navigation = useNavigation();
  const [image, setImage] = useState(null);
  const [uploading, setUploading] = useState(false);

  const CLOUDINARY_CONFIG = {
    cloudName: 'dl280pugt',
    apiKey: '715139885557876',
    apiSecret: '8Hkn167yaygNrg1F-A9Ea_F0BPg'
  };

  const generateSignature = () => {
    const timestamp = Date.now().toString();
    const toSign = `timestamp=${timestamp}${CLOUDINARY_CONFIG.apiSecret}`;
    const hash = CryptoJS.SHA1(toSign).toString();
    return hash;
  };

  const uploadImage = async (imageAsset) => {
    setUploading(true);
    try {
      const formData = new FormData();
      formData.append('file', {
        uri: imageAsset.uri,
        type: 'image/jpeg',
        name: `profile-${Date.now()}.jpg`,
      });
      formData.append('api_key', CLOUDINARY_CONFIG.apiKey);
      formData.append('timestamp', Date.now().toString());
      formData.append('signature', generateSignature());

      const response = await axios.post(
        `https://api.cloudinary.com/v1_1/${CLOUDINARY_CONFIG.cloudName}/image/upload`,
        formData,
        {
          headers: {
            'Content-Type': 'multipart/form-data',
          }
        }
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

  useEffect(() => {
    (async () => {
      const { status: cameraStatus } = await ImagePicker.requestCameraPermissionsAsync();
      const { status: mediaLibraryStatus } = await ImagePicker.requestMediaLibraryPermissionsAsync();
    })();
  }, []);

  const pickImage = () => {
    Alert.alert(
      'Choisir une option',
      'Sélectionnez une option pour ajouter une photo de profil',
      [
        {
          text: 'Galerie',
          onPress: () => pickImageFromGallery(),
        },
        {
          text: 'Appareil photo',
          onPress: () => pickImageFromCamera(),
        },
        {
          text: 'Annuler',
          style: 'cancel',
        },
      ],
      { cancelable: true }
    );
  };

  const pickImageFromGallery = async () => {
    let result = await ImagePicker.launchImageLibraryAsync({
      mediaTypes: ImagePicker.MediaTypeOptions.Images,
      allowsEditing: true,
      aspect: [1, 1],
      quality: 1,
    });
    if (!result.canceled) {
      setImage(result.assets[0].uri);
      const imageUrl = await uploadImage(result.assets[0]);
      if (imageUrl) {
        console.log('URL de l\'image:', imageUrl);
      }
    }
  };

  const pickImageFromCamera = async () => {
    let result = await ImagePicker.launchCameraAsync({
      allowsEditing: true,
      aspect: [1, 1],
      quality: 1,
    });
    if (!result.canceled) {
      setImage(result.assets[0].uri);
      const imageUrl = await uploadImage(result.assets[0]);
      if (imageUrl) {
        console.log('URL de l\'image:', imageUrl);
      }
    }
  };

  return (
    <View style={styles.container}>
      <TouchableOpacity style={styles.imageContainer} onPress={pickImage}>
        <Image
          source={image ? { uri: image } : require('../../../assets/profile-default.jpg')}
          style={styles.image}
        />
      </TouchableOpacity>
      {uploading && (
        <Text style={styles.uploadingText}>Upload en cours...</Text>
      )}
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
    overflow: 'hidden',
    borderWidth: 2,
    borderColor: '#ddd',
  },
  image: {
    width: 160,
    height: 160,
    borderRadius: 80,
  },
  navigateButton: {
    marginTop: 16,
    padding: 12,
    backgroundColor: '#007AFF',
    borderRadius: 4,
    width: 160,
  },
  buttonText: {
    color: 'white',
    textAlign: 'center',
  },
  uploadingText: {
    marginTop: 8,
    color: '#666',
    textAlign: 'center',
  }
});

export default ProfilePicture;