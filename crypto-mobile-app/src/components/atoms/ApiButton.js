import React from "react";
import { Button, StyleSheet } from "react-native";
import { fetchDataFromFirebase } from "../../services/firebaseService";

const ApiButton = () => {
    const handlePress = () => {
        fetchDataFromFirebase();
    };

    return <Button 
        style={styles.coffee_btn} 
        title="POC Firebase"
        onPress={handlePress}
    ></Button>
};

const styles = StyleSheet.create({
    coffee_btn :{
        backgroundColor:"#bf9d5f",
    }
});

export default ApiButton;