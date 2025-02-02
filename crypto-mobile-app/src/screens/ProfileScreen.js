import React from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';
import { useNavigation } from '@react-navigation/native';

const ProfileScreen = () => {
  const navigation = useNavigation();

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Mon Profil</Text>
      <TouchableOpacity 
        style={styles.profilePictureContainer}
        onPress={() => navigation.navigate('ProfileEdit')}
      >
        <Text style={styles.profilePictureText}>Photo de profil</Text>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    padding: 16,
    backgroundColor: '#f5f5f5',
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 20,
  },
  profilePictureContainer: {
    width: 150,
    height: 150,
    backgroundColor: '#ddd',
    borderRadius: 75,
    justifyContent: 'center',
    alignItems: 'center',
    marginBottom: 20,
  },
  profilePictureText: {
    fontSize: 14,
    color: '#666',
  },
  button: {
    padding: 12,
    backgroundColor: '#007AFF',
    borderRadius: 8,
    alignItems: 'center',
  },
  buttonText: {
    color: 'white',
    fontSize: 16,
  },
});

export default ProfileScreen;