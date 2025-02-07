import { StyleSheet, Text, View } from 'react-native'
import React from 'react'
import ProfilePicture from '../components/organisms/ProfilePicture'

const EditUserScreen = () => {
  return (
    <View>
      <View style={styles.profilcontainer} >
        <ProfilePicture></ProfilePicture>
      </View>
    </View>
  )
}

export default EditUserScreen

const styles = StyleSheet.create({})