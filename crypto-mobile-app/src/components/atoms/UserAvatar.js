import { View, Text } from 'react-native'
import React from 'react'
import {Avatar} from 'react-native-elements'

export default function UserAvatar() {
  return (
    <Avatar
        rounded
        size={'xlarge'}
        activeOpacity={0.7}
        source={{
            uri:
            'https://cdn.jsdelivr.net/gh/RdjcMada/GessWhoStatic/img/user/user.jpg',
        }}
    />
  )
}