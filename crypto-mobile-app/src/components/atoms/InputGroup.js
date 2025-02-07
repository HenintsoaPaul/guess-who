import { StyleSheet, Text, TextInput, View } from 'react-native'
import React from 'react'
import { colorsChart } from '../../constants/ColorsChart'

const InputGroup = ({autoCapitalize,placeholder,label,inputValue,setInputValue,groupStyle,labelStyle,inputStyle,nullable}) => {
  return (
    <View style={[styles.inputGroup,groupStyle]}>
        <Text style={[styles.label,labelStyle]}>{label}</Text>
        <TextInput

            style={[styles.input,inputStyle]}
            placeholder={placeholder}
            placeholderTextColor="#666"
            value={inputValue}
            onChangeText={setInputValue}
            autoCapitalize={autoCapitalize}
        />
    </View>
  )
}

export default InputGroup

const styles = StyleSheet.create({
    inputGroup :{
        gap:5,
    },
    label:{
        paddingHorizontal:10
    },
    input: {
        height: 50,
        borderWidth: 2,
        borderColor: "#e9ecef",
        borderRadius: 12,
        paddingHorizontal: 15,
        backgroundColor: colorsChart.light,
      },
})