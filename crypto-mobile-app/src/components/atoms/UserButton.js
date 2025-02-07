import React from 'react'
import { Button } from 'react-native-elements'
import { colorsChart } from '../../constants/ColorsChart'

export default function UserButton( {title,iconName,buttonStyle,titleStyle,onPress}) {
  return (
    <Button 
        title={title} 
        type='outline'
        buttonStyle={[{width:100,borderRadius:50,borderColor:colorsChart.primary},buttonStyle]}
        titleStyle={titleStyle}
        onPress={onPress}
        icon={{ name:iconName, type: 'font-awesome' }}
    />
  )
}