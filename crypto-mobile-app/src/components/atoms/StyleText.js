import { View, Text } from 'react-native'
import React from 'react'

export default function StyleText({color,fw,fs,children,style}) {
  return (
    <Text style={[{
        color : color,
        fontWeight : fw,
        fontSize: fs
    },style]}>{children}</Text>
  )
}