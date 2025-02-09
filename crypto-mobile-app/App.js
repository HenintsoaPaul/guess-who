import React from 'react';
import DrawerNavigation from './src/navigation/DrawerNavigation';
import NavigationBar from './src/navigation/NavigationBar';
import { AppProvider } from './AppContext';
import { SafeAreaView } from 'react-native';

export default function App() {
  return (

    <AppProvider >
      <NavigationBar />
    </AppProvider>
  );
}
