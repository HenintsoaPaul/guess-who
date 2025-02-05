import React from 'react';
import DrawerNavigation from './src/navigation/DrawerNavigation';
import { AppProvider } from './AppContext';
import { usePushNotifications } from './usePushNotifications';

export default function App() {
  // const {expoPushToken,notification} = usePushNotifications;
  // const data = JSON.stringify(notification,undefined,2);

  return (
    <AppProvider>
      <DrawerNavigation />
    </AppProvider>
  );

  // return <View>
  //   <Text>Token : {expoPushToken?.data ?? ""}</Text>
  //   <Text>{data}</Text>
  // </View>
}
