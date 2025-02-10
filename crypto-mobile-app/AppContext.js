import React, { createContext, useState, useEffect } from 'react';
import { findAccountByMail } from './src/services/loginService';
import { useNavigation } from '@react-navigation/native';
import * as Notification from "expo-notifications";

Notification.setNotificationHandler({
  handleNotification: async () => ({
    shouldShowAlert: true,
    shouldPlaySound: false,
    shouldSetBadge: false,
  }),
});
export const AppContext = createContext();

const updateUserToken = async(user,token) => {
    updateOrCreateMobDoc('account',user,{fcmToken:token});
}

export const AppProvider = ({ children }) => {
  const [image, setImage] = useState(null);
  const [user, setUser] = useState(null);
  
  useEffect(() => {
  }, []);

  const logIn = (userData) => {
    setUser(userData);
    const requestPermission = async () => {
      const token = await getNotificationToken();
      console.log(token);
      if(token !== null) {
        await updateUserToken(user,token.data);
      }
    }
    requestPermission();
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