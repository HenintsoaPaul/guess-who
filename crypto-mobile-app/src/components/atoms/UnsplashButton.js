import React, { useState } from "react";
import { Button, StyleSheet, Image, View } from "react-native";
import { fetchPhotoFromUnsplash } from "../../services/unsplashService";

const UnsplashButton = () => {
    const [photoUrl, setPhotoUrl] = useState(null);

    const handlePress = async () => {
        const url = await fetchPhotoFromUnsplash();
        setPhotoUrl(url);
    };

    return (
        <View>
            <Button 
                style={styles.unsplash_btn} 
                title="Fetch Unsplash Photo"
                onPress={handlePress}
            />
            {photoUrl && <Image source={{ uri: photoUrl }} style={styles.photo} />}
        </View>
    );
};

const styles = StyleSheet.create({
    unsplash_btn: {
        backgroundColor: "#000",
        color: "#fff"
    },
    photo: {
        width: 300,
        height: 300,
        marginTop: 20
    }
});

export default UnsplashButton;
