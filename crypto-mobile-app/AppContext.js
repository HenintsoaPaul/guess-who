import React, { createContext, useState, useEffect } from 'react';
import * as Notification from "expo-notifications";
import { getNotificationToken } from './src/services/notificationService';
import { updateOrCreateMobDoc } from './src/services/firebaseService';

Notification.setNotificationHandler({
  handleNotification: async () => ({
    shouldShowAlert: true,
    shouldPlaySound: false,
    shouldSetBadge: false,
  }),
});
export const AppContext = createContext();

const updateUserToken = async(user,token) => {
  try {
    await updateOrCreateMobDoc('account',user,{fcmToken:token});
  } catch (error) {
    alert(error.message)
  }
}

export const AppProvider = ({ children }) => {
  const [image, setImage] = useState(null);
  const [user, setUser] = useState(null);
  
  useEffect(() => {
  }, []);

  const logIn = async (userData) => {
    setUser(userData);
    const requestPermission = async () => {
      const token = await getNotificationToken();
      if(token !== null) {
        await updateUserToken(userData,token.data);
      }
    }
    await requestPermission();
    // await saveSession(userData);
  };

  const logOut = () => {
    setUser(null);
    // await clearSession();
  };

  const refreshUser = async () => {
    
  }

  return (
    <AppContext.Provider value={{ image, setImage, user, setUser, logIn, logOut , refreshUser }}>
      {children}
    </AppContext.Provider>
  );  
};