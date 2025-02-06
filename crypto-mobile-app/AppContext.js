import React, { createContext, useState, useEffect } from 'react';

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

  return (
    <AppContext.Provider value={{ image, setImage, user, setUser, logIn, logOut }}>
      {children}
    </AppContext.Provider>
  );
};