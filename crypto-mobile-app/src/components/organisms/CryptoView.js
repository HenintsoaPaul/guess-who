import React from 'react'
import { FlatList } from 'react-native'
import CryptoCard from '../molecules/CryptoCard'
export default function CryptoView({data}) {
  return (
    <FlatList
        data={data}
        renderItem={({item}) => <CryptoCard crypo={item} />}
        showsVerticalScrollIndicator={true}
        contentContainerStyle={{
            paddingBottom:25,
        }}
        numColumns={2}
        columnWrapperStyle={{justifyContent:"space-between"}}
    />
    
  )
}