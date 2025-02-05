import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { NavigationContainer } from '@react-navigation/native';
import LoginScreen from './src/screens/LoginScreen';
import UserScreen from './src/screens/UserScreen';
import { useEffect, useState } from 'react';
import { FIREBASE_AUTH } from './src/services/firebaseService';
import { createDrawerNavigator } from '@react-navigation/drawer';


const Stack = createNativeStackNavigator();

const InsideStack = createNativeStackNavigator();
const Drawer  = createDrawerNavigator();
function InsideLayout(){
  return (
    <Drawer.Navigator>
      <InsideStack.Screen name='UserProfile' options={ {title:"Profile"}} component={UserScreen}></InsideStack.Screen>
    </Drawer.Navigator>
  )
}

export default function App() {
  const [user,setUser] = useState(null);
  useEffect(()=>{
    FIREBASE_AUTH.onAuthStateChanged((user) => {
      console.log('user :'+user)
      setUser(user);
    })
  })
  return (
      <NavigationContainer>
        <Stack.Navigator initialRouteName='Login'>
          {user ? ( 
            <Stack.Screen name='Inside'component={InsideLayout} options={{headerShown:false}}/> 
           ) : (  
            <Stack.Screen name='Login'component={LoginScreen} options={{headerShown:false}}/>
           )}
        </Stack.Navigator>
      </NavigationContainer>
  );
}