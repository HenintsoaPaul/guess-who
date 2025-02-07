import { View, Text, StyleSheet } from 'react-native'
import React, { useContext } from 'react'
import UserInfo from '../molecules/UserInfo'
import { AppContext } from '../../../AppContext';
import { colors } from 'react-native-elements';

export default function UserInfos() {
    const {user} = useContext(AppContext) ;
    return (
        <View style={styles.container}>
            <UserInfo icon={'id-card'} label={'UID'}>{user.id}</UserInfo>
            <UserInfo icon={'dollar'} label={'Fund'}>{user.fund}</UserInfo>
        </View>
    )
}

const styles = StyleSheet.create({
    container:{
        flex:1,
        flexDirection:'column',
        gap:10,
        padding: 10,

    }
})