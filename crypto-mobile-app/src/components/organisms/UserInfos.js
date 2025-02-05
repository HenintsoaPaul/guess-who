import { View, Text, StyleSheet } from 'react-native'
import React from 'react'
import UserInfo from '../molecules/UserInfo'
import { FIREBASE_AUTH } from '../../services/firebaseService'

export default function UserInfos() {
    const user = FIREBASE_AUTH.currentUser;
    return (
        <View style={styles.container}>
            <UserInfo icon={'user'} label={'UID'}>{user.uid}</UserInfo>
            <UserInfo icon={'mail'} label={'Email'}>{user.email}</UserInfo>
            <UserInfo icon={'dollar'} label={'Fund'}>100.20$</UserInfo>
        </View>
    )
}

const styles = StyleSheet.create({
    container:{
        flex:1,
        flexDirection:'column',
        gap:10
    }
})