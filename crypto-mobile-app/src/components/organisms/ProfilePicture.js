import React, { useEffect, useContext, useState } from 'react';
import { View, StyleSheet,Image } from 'react-native';
import axios from 'axios';
import { AppContext } from '../../../AppContext';
import { AdvancedImage } from '@cloudinary/react';

const CLOUDINARY_CONFIG = {
  cloudName: 'dulx9capq',
  apiKey: '174986489287854',
  apiSecret: 'k7dnDcMEbe24SF1jNB3YSPM1krA'
};

const ProfilePicture = () => {
  const { image, setImage, user } = useContext(AppContext);
  useEffect(() => {
    try {
      const profile = user.profilImg;
      if(profile === null || profile === undefined){
        throw new Error("No profile seted")
      }
      setImage(profile)
    } catch (error) {
      console.log("Using defaut profile")
    }
  }, []);
  return (
    <View style={styles.container}>
      <View style={styles.imageContainer} >
        <Image
          source={image ? { uri: image } : require('../../../assets/profile.jpg')}
          style={styles.image}
        />
      </View>
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
  }
});

export default ProfilePicture;
