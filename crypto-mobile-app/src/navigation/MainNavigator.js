import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import ProfileScreen from '../screens/ProfileScreen';
import WalletScreen from '../screens/WalletScreen';
import FavoritesScreen from '../screens/FavoritesScreen'; 

const Stack = createNativeStackNavigator();

export default function Navigation() {
  return (
    <NavigationContainer>
      <Stack.Navigator>
        <Stack.Screen
          name="Profile"
          component={ProfileScreen}
          options={{ title: 'Profil' }}
        />
        <Stack.Screen
          name="Wallet"
          component={WalletScreen}
          options={{ title: 'Portefeuille' }}
        />
        <Stack.Screen
          name="Favorites"
          component={FavoritesScreen}
          options={{ title: 'Mes Favoris' }}
        />
      </Stack.Navigator>
    </NavigationContainer>
  );
}