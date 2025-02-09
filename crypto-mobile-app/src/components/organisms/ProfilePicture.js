import React, { useEffect, useContext } from 'react';
import { View, StyleSheet, Image, TouchableOpacity } from 'react-native';
import { AppContext } from '../../../AppContext';
import EditIcon from '../atoms/EditIcon';
import { useNavigation } from '@react-navigation/native';

const ProfilePicture = () => {
  const { image, setImage, user } = useContext(AppContext);
  const navigation = useNavigation();

  useEffect(() => {
    try {
      const profile = user.account_img;
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
      <View style={styles.imageContainer}>
        <Image
          source={image ? { uri: image } : require('../../../assets/profile.jpg')}
          style={styles.image}
        />
      </View>

      <TouchableOpacity style={styles.editButton} onPress={() => navigation.push('ProfileEdit')}>
        <EditIcon />
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
