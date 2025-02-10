import React from 'react';
import DrawerNavigation from './src/navigation/DrawerNavigation';
import { AppProvider } from './AppContext';

export default function App() {
  return (

    <AppProvider >
      <DrawerNavigation />
    </AppProvider>
  );
}
