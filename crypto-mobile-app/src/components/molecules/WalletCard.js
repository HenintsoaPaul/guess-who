import { View,
    StyleSheet, 
  } from 'react-native'
import React, { useContext, useEffect, useState } from 'react'
import StyleText from '../atoms/StyleText';
import { colorsChart } from '../../constants/ColorsChart';
import { ActivityIndicator } from 'react-native';

import CryptoLogo from '../atoms/CryptoLogo';




export default function WalletCard({wallet}) {
 const [crypto,setCrypto] = useState(wallet.crypto);
console.log(wallet);

 useEffect(()=>{
  const crycry = ( ) =>{
    setCrypto(wallet.crypto)
  }
  crycry()
 },[]);

 return (
  <View style={styles.container}>
  <View style={styles.head}>
    <CryptoLogo crypto={crypto}></CryptoLogo>
    <StyleText>{wallet.quantity}</StyleText>
  </View>
  <View style={styles.detail}>
    <StyleText fw={700} color={colorsChart.primary} >{crypto.name}</StyleText>
    <StyleText  color={colorsChart.dark}> ({crypto.symbol})</StyleText>
  </View>
</View>
 )
}

const styles = StyleSheet.create({
   container:{
    flex: 1,
    gap:10,
    flexDirection:'collumn',
    justifyContent:'space-between',
    backgroundColor:colorsChart.white,
    alignItems:'center',
    padding:10,
    margin:5,
    borderRadius:10,
    borderWidth:3,
    borderColor:colorsChart.gray
  }
  ,detail :{
    flex:1,
     flexDirection:'row',
     alignItems:'center',
     justifyContent:'center'
   },
   head:{
    flex:1,
    flexDirection:'row',
    alignItems:'center',
    justifyContent:'space-between'

   }
});