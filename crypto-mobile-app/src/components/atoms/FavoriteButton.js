import React from 'react';
import { TouchableOpacity, Image, StyleSheet } from 'react-native';
import heartBlack from '../../../assets/heart_black.png';
import heartRed from '../../../assets/heart_red.png';

const FavoriteButton = ({ isFavorite, onPress }) => {
  return (
    <TouchableOpacity onPress={onPress} style={styles.container}>
      <Image
        source={isFavorite ? heartRed : heartBlack}
        style={styles.heartIcon}
      />
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  container: {
    padding: 8,
  },
  heartIcon: {
    width: 24,
    height: 24,
  },
});

export default FavoriteButton;