import React, { useContext } from 'react';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { NavigationContainer } from '@react-navigation/native';
import { View, Text, StyleSheet } from 'react-native';
import { AppContext } from '../../AppContext';
import WalletScreen from '../screens/WalletScreen';
import FavoritesScreen from '../screens/FavoritesScreen';
import CoursScreen from '../screens/CoursScreen';
import TransactionFundScreen from '../screens/TransactionFundScreen';
import UserScreen from '../screens/UserScreen';
import LoginScreen from '../screens/LoginScreen';
import EditUserScreen from '../screens/EditUserScreen';
import AchatVenteScreen from '../screens/AchatVenteScreen';
import PictureEditScreen from '../screens/PictureEditScreen';
import ProfileStack from './ProfileStack';

const Tab = createBottomTabNavigator();

const NavigationBar = () => {
  const { user } = useContext(AppContext);

  return (
    <NavigationContainer>
      <Tab.Navigator
        screenOptions={({ route }) => ({
          tabBarActiveTintColor: '#007AFF',
          tabBarInactiveTintColor: '#666',
          tabBarStyle: styles.tabBar,
        })}
      >
        <Tab.Screen 
          name="Login" 
          component={LoginScreen} 
          options={{ headerShown: false, tabBarStyle: { display: 'none' } }}
        />
        <Tab.Screen 
          name="Portefeuille" 
          component={WalletScreen}
          options={{
            tabBarLabel: 'Portefeuille',
            tabBarIcon: () => (
              <View style={styles.iconContainer}>
                <Text>💰</Text>
              </View>
            ),
          }}
        />
        <Tab.Screen 
          name="Crypto cours" 
          component={CoursScreen}
          options={{
            tabBarLabel: 'Crypto cours',
            tabBarIcon: () => (
              <View style={styles.iconContainer}>
                <Text>🔹</Text>
              </View>
            ),
          }}
        />
        <Tab.Screen 
          name="Favoris" 
          component={FavoritesScreen}
          options={{
            tabBarLabel: 'Favoris',
            tabBarIcon: () => (
              <View style={styles.iconContainer}>
                <Text>❤️</Text>
              </View>
            ),
          }}
        />
        <Tab.Screen 
          name="Transaction fund" 
          component={TransactionFundScreen}
          options={{
            tabBarLabel: 'Transactions',
            tabBarIcon: () => (
              <View style={styles.iconContainer}>
                <Text>🔁</Text>
              </View>
            ),
          }}
        />
        <Tab.Screen 
          name="AchatVente" 
          component={AchatVenteScreen}
          options={{
            tabBarLabel: 'Achat/Vente',
            tabBarIcon: () => (
              <View style={styles.iconContainer}>
                <Text>🛒</Text>
              </View>
            ),
          }}
        />
        <Tab.Screen 
          name="Profil" 
          component={ProfileStack}
          options={{
            tabBarLabel: 'Profil',
            tabBarIcon: () => (
              <View style={styles.iconContainer}>
                <Text>👤</Text>
              </View>
            ),
          }}
        />
      </Tab.Navigator>
    </NavigationContainer>
  );
};

const styles = StyleSheet.create({
  tabBar: {
    backgroundColor: '#fff',
    borderTopWidth: 0.5,
    borderTopColor: '#ddd',
  },
  iconContainer: {
    width: 30,
    height: 30,
    justifyContent: 'center',
    alignItems: 'center',
  },
});

export default NavigationBar;
