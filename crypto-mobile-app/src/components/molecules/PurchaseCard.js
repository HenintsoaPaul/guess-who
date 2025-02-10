import { StyleSheet, Text, View } from 'react-native'
import React from 'react'
import { colorsChart } from '../../constants/ColorsChart';

const PurchaseCard = ({purchase}) => {
    const cryptoName = purchase.saleDetailDocument?.crypto?.name || purchase.crypto?.name || 'N/A';
    return (
      <View style={styles.card} accessible={true} accessibilityLabel={`Transaction ${purchase.id}`}>
        <View style={styles.row}>
          <View style={styles.col}>
            <Text style={styles.label}>Acheteur :</Text>
            <Text style={styles.value}>{purchase.accountPurchaser?.pseudo || 'N/A'}</Text>
          </View>
          <View style={styles.col}>
            <Text style={styles.label}>Vendeur :</Text>
            <Text style={styles.value}>{purchase.accountSeller?.pseudo || 'N/A'}</Text>
          </View>
        </View>
        <View style={styles.row}>
          <View style={styles.col}>
            <Text style={styles.label}>Crypto :</Text>
            <Text style={styles.value}>{cryptoName}</Text>
          </View>
          <View style={styles.col}>
            <Text style={styles.label}>Quantité :</Text>
            <Text style={styles.value}>{purchase.quantityCrypto}</Text>
          </View>
        </View>
        <View style={styles.row}>
          <View style={styles.col}>
            <Text style={styles.label}>Prix Total :</Text>
            <Text style={styles.value}>{purchase.totalPrice} €</Text>
          </View>
          <View style={styles.col}>
            <Text style={styles.label}>Date :</Text>
            <Text style={styles.value}>
              {purchase.datePurchase ? new Date(purchase.datePurchase.seconds * 1000).toLocaleDateString() : 'N/A'}
            </Text>
          </View>
        </View>
      </View>
    );
}

export default PurchaseCard

const styles = StyleSheet.create({
    
      card: {
        backgroundColor: colorsChart.light, // Change background color to dark grey
        padding: 16,
        borderRadius: 10,
        marginBottom: 12,
      },
      row: {
        flexDirection: 'row',
        marginBottom: 8,
      },
      col: {
        flex: 1,
        justifyContent: 'center',
      },
      label: {
        color: colorsChart.secondary,
        fontSize: 14,
        fontWeight: '600',
      },
      value: {
        color: colorsChart.dark, // Change text color to white for better contrast
        fontSize: 14,
      },
      emptyText: {
        color: colorsChart.dark, // Change text color to white for better contrast
        textAlign: 'center',
        marginTop: 20,
        fontSize: 16,
      },
      searchToggleButton: {
        flexDirection: 'row',
        alignItems: 'center',
        backgroundColor: 'transparent',
        borderBottomColor: '#00b894',
        borderBottomWidth: 1,
        paddingVertical: 5,
        paddingHorizontal: 10,
        borderRadius: 0,
        marginBottom: 16,
      },
      searchToggleButtonText: {
        color: '#00b894',
        fontSize: 16,
        fontWeight: '700',
      },
      topSearchContainer: {
        flexDirection: 'row',
        alignItems: 'center',
        backgroundColor: '#2a2a2a', // Change background color to dark grey
        borderRadius: 8,
        paddingHorizontal: 12,
        paddingVertical: 10,
        marginBottom: 16,
      },
      topSearchInput: {
        flex: 1,
        color: '#ffffff', // Change text color to white for better contrast
        fontSize: 14,
      },
      topSearchButton: {
        marginLeft: 8,
      },
      outlineButton: {
        backgroundColor: 'transparent',
        borderColor: '#00b894',
        borderWidth: 1,
      },
      outlineButtonText: {
        color: '#00b894',
      },
      upArrowButton: {
        flexDirection: 'row',
        alignItems: 'center',
        backgroundColor: 'transparent',
        borderColor: '#00b894',
        borderWidth: 1,
        paddingVertical: 5,
        paddingHorizontal: 10,
        borderRadius: 8,
        alignSelf: 'center',
        marginTop: 16,
      },
      upArrowButtonText: {
        color: '#00b894',
        fontSize: 16,
        fontWeight: '700',
      },
});
