import { StyleSheet, Text, TouchableOpacity, View } from 'react-native'
import React from 'react'
import { FontAwesome } from '@expo/vector-icons'
import { colorsChart } from '../../constants/ColorsChart'

const FavButton = ({onFav=false,style,onPress}) => {
    const favView = ()=>{
        if (onFav == true) {
            return <FontAwesome color={colorsChart.red} name='heart'size={24}></FontAwesome>
        }
        else {
            return <FontAwesome color={colorsChart.red} name='heart-o'size={24}></FontAwesome>
        }
    }
  return (
    <View style={style}>
        <TouchableOpacity onPress={onPress} >

        {favView()}
        </TouchableOpacity>
    </View>
  )
}

export default FavButton

const styles = StyleSheet.create({})