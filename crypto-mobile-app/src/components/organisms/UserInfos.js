import { View, Text, StyleSheet } from 'react-native'
import React, { useContext } from 'react'
import UserInfo from '../molecules/UserInfo'
import { AppContext } from '../../../AppContext';

export default function UserInfos() {
    const {user} = useContext(AppContext) ;
    return (
        <View style={styles.container}>
            <UserInfo icon={'user'} label={'UID'}>{user.id}</UserInfo>
            <UserInfo icon={'mail'} label={'Email'}>{user.email}</UserInfo>
            <UserInfo icon={'dollar'} label={'Fund'}>{user.fund}</UserInfo>
            <UserInfo icon={'user'} label={'Pseudo'}>{user.pseudo}</UserInfo>
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