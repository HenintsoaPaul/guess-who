import React from 'react';
import DrawerNavigation from './src/navigation/DrawerNavigation';
import { AppProvider } from './AppContext';
import { SafeAreaView } from 'react-native';

export default function App() {
  return (

    <AppProvider >
      <DrawerNavigation />
    </AppProvider>
  );
}
