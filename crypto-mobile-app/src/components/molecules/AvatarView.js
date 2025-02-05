import { View, Text, StyleSheet } from 'react-native'
import React from 'react'
import UserAvatar from '../atoms/UserAvatar'

export default function AvatarView() {
  return (
    <View style={styles.container}>
      <UserAvatar></UserAvatar>
    </View>
  )
}
const styles = StyleSheet.create({
    container :{
        flex :1,
        flexDirection:'row',
        justifyContent: 'center',
        alignItems:'center',
    }
});