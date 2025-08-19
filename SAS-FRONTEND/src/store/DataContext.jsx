import { createContext, useReducer, useState } from "react";
import Cookies from 'js-cookie';

export const DataContext = createContext({});

const bearerTokenReducer = (state, action) => {
  switch (action.type) {
    case 'LOGIN':
      Cookies.set('bearerToken', action.payload);
      return action.payload;
    case 'LOGOUT':
      Cookies.remove('bearerToken');
      return null;
    default:
      return state;
  }
};

export const DataProvider = ({ children }) => {
  const [userData, setUserData] = useState(null);
  const [time, setTime] = useState("");
  const [token, dispatchBearerToken] = useReducer(
    bearerTokenReducer,
    Cookies.get('bearerToken') || null
  );

  const login = (token) => {
    dispatchBearerToken({ type: 'LOGIN', payload: token });
  };

  const logout = () => {
    dispatchBearerToken({ type: 'LOGOUT' });
  };

  return (
    <DataContext.Provider
      value={{
        userData,
        setUserData,
        login,
        logout,
        token,time, setTime,
      }}
    >
      {children}
    </DataContext.Provider>
  );
};
