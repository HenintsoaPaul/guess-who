import { View, Text, StyleSheet } from 'react-native'
import React from 'react'
import StyleText from '../atoms/StyleText';
import { colors } from '../../constants/Colors';
import { Button } from '@ant-design/react-native';

export default function CryptoCard({crypo}) {
  return (
    <View style={styles.container}>
        <View style={{flexDirection:'row',gap:10,paddingBottom:10}}>
            <StyleText>{crypo.cryptoName}</StyleText>
            <StyleText fw={'bold'} fs={'24'}>{crypo.symbol}</StyleText>
            <StyleText color={colors.secondary}>{crypo.price} $</StyleText>
        </View>
    </View>
  )
}

const styles = StyleSheet.create({
    container:{
        flex: 1,
        flexDirection:'collumn',
        backgroundColor:colors.light,
        justifyContent:'center',
        alignItems:'center',
        margin:5,
        padding: 5,
    }
});