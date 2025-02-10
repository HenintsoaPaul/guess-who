import {React,useContext,useEffect,useState} from 'react';
import { createDrawerNavigator } from '@react-navigation/drawer';
import { NavigationContainer } from '@react-navigation/native';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';
import WalletScreen from '../screens/WalletScreen';
import FavoritesScreen from '../screens/FavoritesScreen';
import TransactionFundScreen from '../screens/TransactionFundScreen';
import ProfilePicture from '../components/organisms/ProfilePicture';
import UserScreen from '../screens/UserScreen';
import LoginScreen from '../screens/LoginScreen';
import { AppContext } from '../../AppContext';
import { colorsChart } from '../constants/ColorsChart';
import EditUserScreen from '../screens/EditUserScreen';
import AchatVenteScreen from '../screens/AchatVenteScreen';
import { LinearGradient } from "expo-linear-gradient";
import CoursScreen from '../screens/CoursScreen';
import PictureEditScreen from '../screens/PictureEditScreen';


const SCREEN_LABELS = {
  'Profil': 'Profil',
  'Portefeuille': 'Portefeuille',
  'Favoris': 'Favoris',
  'Crypto cours': 'Crypto cours',
  'ProfileEdit': 'Éditer Profil',
  'Transaction fund': 'Transaction fund',
  'AchatVente': 'AchatVente'
};

const Drawer = createDrawerNavigator();

const DrawerNavigation = () => {
  const {user,setUser} = useContext(AppContext);
  return (
    <NavigationContainer>
      <Drawer.Navigator
        screenOptions={{
          drawerType: 'back',
          drawerStyle: {
            backgroundColor: '#fff',
            width: 280,
          },
          drawerLabelStyle: {
            marginLeft: -15,
            fontSize: 16,
          },
        }}
        drawerContent={(props) => <CustomDrawerContent {...props} />}
        initialRouteName='Login'
      >
        {user ? (
          <>
          <Drawer.Screen
          name="Profil"
          component={UserScreen}
          options={
              {
                drawerIcon: ({ focused }) => (
                <Text style={[styles.icon, focused && styles.activeIcon]}>👤</Text>
              ),
            }}
          />
          <Drawer.Screen
            name="ProfileEdit"
            component={EditUserScreen}
            options={{
              drawerIcon: ({ focused }) => (
                <Text style={[styles.icon, focused && styles.activeIcon]}>📝</Text>
              ),
            }}
          />
          <Drawer.Screen
            name="PictureEdit"
            component={PictureEditScreen}
            options={{
              drawerStyle:({backgroundColor:colorsChart.red}),
              drawerIcon: ({ focused }) => (
                <Text style={[styles.icon, focused && styles.activeIcon]}>📝</Text>
              ),
            }}
          />
          <Drawer.Screen
          name="Portefeuille"
          component={WalletScreen}
          options={{
            drawerIcon: ({ focused }) => (
              <Text style={[styles.icon, focused && styles.activeIcon]}>💰</Text>
            ),
          }}
          />
          <Drawer.Screen
            name="Crypto cours"
            component={CoursScreen} 
            options={{
              drawerIcon: ({ focused }) => (
                <Text style={[styles.icon, focused && styles.activeIcon]}>🔹</Text>
              ),
            }}
            />
          <Drawer.Screen
            name="Favoris"
            component={FavoritesScreen}
            initialParams={{ favoritesList: [] }} 
            options={{
              drawerIcon: ({ focused }) => (
                <Text style={[styles.icon, focused && styles.activeIcon]}>❤️</Text>
              ),
            }}
            />

          <Drawer.Screen
            name="Transaction fund"
            component={TransactionFundScreen}
            options={{
              drawerIcon: ({ focused }) => (
                <Text style={[styles.icon, focused && styles.activeIcon]}>🔁</Text>
              ),
            }}
          />

          <Drawer.Screen
            name='AchatVente'
            component={AchatVenteScreen}
            options={{
              drawerIcon: ({ focused }) => (
                <Text style={[styles.icon, focused && styles.activeIcon]}>🛒</Text>
              ),
            }}
            />
            </>
          ) 
          : 
          (
            <Drawer.Screen name='Login'component={LoginScreen} options={{headerShown:false}}/>
          )
        }
      </Drawer.Navigator>
    </NavigationContainer>
  );
};

const CustomDrawerContent = (props) => {
  const { state } = props;
  
  return (
    <View style={styles.drawerContent}>
      <View>
        <LinearGradient
          colors={["rgba(58,65,110,255)", "rgba(25,28,48,255)"]}
          style={styles.headerOverlay}
        >
        <ProfilePicture></ProfilePicture>    
        </LinearGradient>
      </View>
      <View style={styles.drawerMenu}>
        {state?.routes?.map((route, index) => (
          <TouchableOpacity
            key={route.key}
            style={styles.menuItem}
            onPress={() => props.navigation.navigate(route.name)}
          >
            <Text style={styles.menuText}>
              {SCREEN_LABELS[route.name] || route.name}
            </Text>
          </TouchableOpacity>
        ))}
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  drawerContent: {
    flex: 1,
  },
  drawerHeader: {
    padding: 20,
    backgroundColor: colorsChart.primary,
    borderBottomColor: colorsChart.secondary,
  },
  profilePicture: {
    fontSize: 40,
    textAlign: 'center',
    marginBottom: 10,
  },
  profileName: {
    fontSize: 18,
    fontWeight: '600',
    color: '#333',
    textAlign: 'center',
  },
  drawerMenu: {
    flex: 1,
    paddingTop: 10,
    backgroundColor:colorsChart.white
  },
  menuItem: {
    padding: 15,
    paddingLeft: 30,
  },
  menuText: {
    fontSize: 16,
    color: '#333',
  },
  icon: {
    fontSize: 24,
  },
  activeIcon: {
    color: '#007AFF',
  },
  headerOverlay: {
    justifyContent: "center",
    alignItems: "center",
    padding:10,
  },
});

export default DrawerNavigation;