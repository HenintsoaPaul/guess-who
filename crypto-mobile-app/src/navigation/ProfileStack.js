// ProfileStack.js
import React from 'react';
import { createStackNavigator } from '@react-navigation/stack';
import UserScreen from '../screens/UserScreen';
import EditUserScreen from '../screens/EditUserScreen';
import PictureEditScreen from '../screens/PictureEditScreen';

const Stack = createStackNavigator();

const ProfileStack = () => {
  return (
    <Stack.Navigator 
      initialRouteName="Profil"
      screenOptions={{
        headerShown: true,
        headerStyle: {
          backgroundColor: '#fff',
        },
        headerTintColor: '#000',
      }}
    >
      <Stack.Screen 
        name="Profil" 
        component={UserScreen}
        options={{
          headerTitle: 'Mon Profil',
          headerTitleStyle: {
            fontSize: 20,
            fontWeight: 'bold'
          }
        }}
      />
      <Stack.Screen 
        name="ProfileEdit" 
        component={EditUserScreen}
        options={{
          headerTitle: 'Éditer Profil',
          headerTitleStyle: {
            fontSize: 20,
            fontWeight: 'bold'
          }
        }}
      />
      <Stack.Screen 
        name="PictureEdit" 
        component={PictureEditScreen}
        options={{
          headerTitle: 'Photo Profil',
          headerTitleStyle: {
            fontSize: 20,
            fontWeight: 'bold'
          }
        }}
      />
    </Stack.Navigator>
  );
};

export default ProfileStack;