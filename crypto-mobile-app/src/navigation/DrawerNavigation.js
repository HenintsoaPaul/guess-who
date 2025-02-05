import {React,useEffect,useState} from 'react';
import { createDrawerNavigator } from '@react-navigation/drawer';
import { NavigationContainer } from '@react-navigation/native';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';
import WalletScreen from '../screens/WalletScreen';
import FavoritesScreen from '../screens/FavoritesScreen';
import CoursActuelleScreen from '../screens/CoursActuelleScreen';
import ProfilePicture from '../components/organisms/ProfilePicture';
import UserScreen from '../screens/UserScreen';
import { FIREBASE_AUTH } from '../services/firebaseService';
import LoginScreen from '../screens/LoginScreen';


const SCREEN_LABELS = {
  'Profil': 'Profil',
  'Portefeuille': 'Portefeuille',
  'Favoris': 'Favoris',
  'Crypto cours': 'Crypto cours',
  'ProfileEdit': 'Éditer Profil'
};

const Drawer = createDrawerNavigator();

const DrawerNavigation = () => {
  const [user,setUser] = useState(null);
    useEffect(()=>{
      FIREBASE_AUTH.onAuthStateChanged((user) => {
        console.log('user :'+user)
        setUser(user);
      })
    })
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
            options={{
              drawerIcon: ({ focused }) => (
                <Text style={[styles.icon, focused && styles.activeIcon]}>👤</Text>
              ),
            }}
          />
          <Drawer.Screen
            name="ProfileEdit"
            component={ProfilePicture}
            options={{
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
          name="Favoris"
          component={FavoritesScreen}
          options={{
            drawerIcon: ({ focused }) => (
              <Text style={[styles.icon, focused && styles.activeIcon]}>❤️</Text>
            ),
          }}
          />
          <Drawer.Screen
          name="Crypto cours"
          component={CoursActuelleScreen}
          options={{
            drawerIcon: ({ focused }) => (
              <Text style={[styles.icon, focused && styles.activeIcon]}>🔹</Text>
            ),
          }}
          />
        </>
      ):
      (
        <Drawer.Screen name='Login'component={LoginScreen} options={{headerShown:false}}/>
      )}
      </Drawer.Navigator>
    </NavigationContainer>
  );
};

const CustomDrawerContent = (props) => {
  const { state } = props;
  
  return (
    <View style={styles.drawerContent}>
      <View style={styles.drawerHeader}>
        <ProfilePicture />
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
    backgroundColor: '#f5f5f5',
    borderBottomWidth: 1,
    borderBottomColor: '#ddd',
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
});

export default DrawerNavigation;