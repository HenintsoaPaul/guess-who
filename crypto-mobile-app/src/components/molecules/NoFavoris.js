import { StyleSheet, Text, View } from 'react-native'
import React from 'react'
import { FontAwesome } from '@expo/vector-icons'

const NoFavoris = () => {
  return (
    <View style={styles.container}>
      <FontAwesome name='star-o' size={100}/>
      <Text>NoFavoris</Text>
    </View>
  )
}

export default NoFavoris

const styles = StyleSheet.create({
    container:{
        height:'50%',
        justifyContent:'center',
        alignItems:'center',
    }
})