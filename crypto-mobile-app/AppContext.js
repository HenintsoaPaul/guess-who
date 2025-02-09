import React, { createContext, useState, useEffect } from 'react';
import { findAccountByMail } from './src/services/loginService';

export const AppContext = createContext();

export const AppProvider = ({ children }) => {
  const [image, setImage] = useState(null);
  const [user, setUser] = useState(null);

  useEffect(() => {
  }, []);

  const logIn = (userData) => {
    setUser(userData);
    // await saveSession(userData);
  };

  const logOut = () => {
    setUser(null);
    // await clearSession();
  };

  const refreshUser = async () => {
    if (user === null) {
      return;
    }
    const userData = await findAccountByMail(user.email)
    setUser(userData)
  }

  return (
    <AppContext.Provider value={{ image, setImage, user, setUser, logIn, logOut , refreshUser }}>
      {children}
    </AppContext.Provider>
  );
};