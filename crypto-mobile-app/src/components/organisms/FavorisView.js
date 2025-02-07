import { View, Text ,FlatList} from 'react-native'
import {React} from 'react'

export default function FavorisView({favorites}) {
    return (
    <FlatList
        data={favorites}
        keyExtractor={(item) => item.idAccount.toString()}
        renderItem={({ item }) => (
            <View style={styles.favoriteItem}>
            <Text style={styles.favoriteText}>
                {item.cryptoName} ({item.symbol})
            </Text>
            </View>
        )}
    />
  )
}