import { View, Text, StyleSheet } from 'react-native'
import React from 'react'
import { AntDesign } from '@expo/vector-icons'
import { colors } from '../../constants/Colors'
import StyleText from '../atoms/StyleText'

export default function UserInfo({icon,label,children}) {
  return (
    <View style={styles.container}>
      <View style={styles.label}>
        <AntDesign name={icon} size={18} ></AntDesign>
        <StyleText style={styles.labelText}  fs={18}> {label} </StyleText>
      </View>
      <StyleText  fs={18}>{children}</StyleText>
    </View>
  )
}

const styles = StyleSheet.create({
  container : {
    flex:1,
    flexDirection:'row',
    backgroundColor: "#1111",
    alignItems:'center',
    paddingHorizontal:15,
    paddingVertical:15,
    borderRadius: 10,
    justifyContent:'space-between'
  },
  label:{
      flexDirection:'row',
      alignItems:'center'
  },
  labelText:{
    marginLeft:10,
  }
});