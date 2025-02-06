import { View, Text, StyleSheet } from 'react-native'
import React from 'react'
import { FontAwesome } from '@expo/vector-icons'
import { colors } from '../../constants/Colors'
import StyleText from '../atoms/StyleText'

export default function UserInfo({icon,label,children}) {
  return (
    <View style={{flexDirection:'row'}}>
      <View style={styles.label}>
        <FontAwesome name={icon} size={16} ></FontAwesome>
        <StyleText style={styles.labelText}  fs={16}> {label} </StyleText>
      </View> 
    <View style={styles.container}>
      <View>
        <StyleText fs={16} color={colors.dark}>{children}</StyleText>
      </View>
    </View>
  </View>
  )
}

const styles = StyleSheet.create({
  container : {
    flex:1,
    flexDirection:'row',
    backgroundColor: colors.light,
    alignItems:'center',
    padding:10,
    justifyContent:'flex-end',


  },
  label:{
    backgroundColor:colors.white,
  },
  labelText:{
    textAlign:'center',
    color:colors.pink
  }
});