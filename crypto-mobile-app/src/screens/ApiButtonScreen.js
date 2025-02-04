import React from "react";
import { View, StyleSheet } from "react-native";
import ApiButton from "../components/atoms/ApiButton";

const ApiButtonScreen = () => {
    return (
        <View style={styles.container}>
            <ApiButton />
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: "center",
        alignItems: "center",
        backgroundColor: "#fff"
    }
});

export default ApiButtonScreen;
