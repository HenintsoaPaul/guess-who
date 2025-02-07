import { View, Text, StyleSheet } from 'react-native'
import React from 'react'
import { FontAwesome } from '@expo/vector-icons'
import { colorsChart } from '../../constants/ColorsChart'
import StyleText from '../atoms/StyleText'

export default function UserInfo({icon,label,children}) {
  return (
    <View style={{flexDirection:'row',alignItems:'center' , gap:10}}>
      <View style={styles.label}>
        <FontAwesome name={icon} size={16} color={colorsChart.white} />
      </View> 
      <View>
        <StyleText>{label}</StyleText>
      </View>
    <View style={styles.container}>
      <View>
        <StyleText fs={16} color={colorsChart.dark}>{children}</StyleText>
      </View>
    </View>
  </View>
  )
}

const styles = StyleSheet.create({
  container : {
    flex:1,
    flexDirection:'row',
    alignItems:'center',
    padding:10,
    justifyContent:'flex-end',
    borderEndEndRadius:4,
    borderStartEndRadius:4,
  },
  label:{
    borderRadius:100,
    width:50,
    height:50,
    backgroundColor:colorsChart.primary,
    alignItems:'center',
    justifyContent:'center'
  }
});