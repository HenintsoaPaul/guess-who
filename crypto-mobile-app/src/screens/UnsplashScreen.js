import React from "react";
import { View, StyleSheet } from "react-native";
import UnsplashButton from "../components/atoms/UnsplashButton";

const UnsplashScreen = () => {
    return (
        <View style={styles.container}>
            <UnsplashButton />
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

export default UnsplashScreen;
