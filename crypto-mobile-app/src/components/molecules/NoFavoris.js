import { View, Text ,StyleSheet} from 'react-native'
import React from 'react'
import { colors } from '../../constants/Colors'
import { AntDesign } from '@expo/vector-icons'

export default function NoFavoris() {
  return (
    <View style={styles.container}>
      <AntDesign 
          name='star'
          size={100}
          color={colors.light}
      />
      <Text>
          No Favoris
      </Text>
    </View>
  )
}

const styles = StyleSheet.create({
    container:{
        flex:1,
        justifyContent:'center',
        alignItems:'center',
    }
})