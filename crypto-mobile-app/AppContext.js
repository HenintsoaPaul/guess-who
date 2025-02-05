import React, { createContext, useState } from 'react';

export const AppContext = createContext();

export const AppProvider = ({ children }) => {
  const [image, setImage] = useState(null);

  return (
    <AppContext.Provider value={{ image, setImage }}>
      {children}
    </AppContext.Provider>
  );
};