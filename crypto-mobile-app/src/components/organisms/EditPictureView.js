import React, { useEffect, useContext } from 'react';
import { View, StyleSheet, TouchableOpacity,Image } from 'react-native';
import { AppContext } from '../../../AppContext';
import { FontAwesome } from '@expo/vector-icons';
import { useNavigation } from '@react-navigation/native';


const EditPictureView = () => {
  const {image} = useContext(AppContext);
  useEffect(() => {
  }, []);
  const navigation = useNavigation();
  const edit=() => {
    navigation.navigate('PictureEdit')
  }

  return (
    <View style={styles.container}>
      <TouchableOpacity style={styles.imageContainer} onPress={edit}>
        <Image
          source={image ? { uri: image } : require('../../../assets/profile.jpg')}
          style={styles.image}
        />
        <View style={styles.overlay}>
          <FontAwesome name='pencil' size={32} style={styles.overlaytext}/>
        </View>
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

export default EditPictureView;
