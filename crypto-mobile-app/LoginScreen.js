import {
  View,
  Text,
  TextInput,
  TouchableOpacity,
  ImageBackground,
  ScrollView,
  StyleSheet,
} from "react-native";
import React , { useEffect, useState } from 'react';
import { FIREBASE_AUTH } from "../services/firebaseService";
import { LinearGradient } from "expo-linear-gradient";
import { signInWithEmailAndPassword } from "firebase/auth";
import Svg, { Path } from "react-native-svg";

const LoginScreen = ({ navigation }) => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);

  const auth = FIREBASE_AUTH;

  const handleLogin = async () => {
    setLoading(true);
    try {
      await signInWithEmailAndPassword(auth, email, password);
      alert("Connexion réussie !");
    } catch (error) {
      alert(`Erreur de connexion: ${error.message}`);
    } finally {
      setLoading(false);
    }
  };

  return (
    <ScrollView
      contentContainerStyle={styles.scrollContainer}
      showsVerticalScrollIndicator={false}
      showsHorizontalScrollIndicator={false}
    >
      {/* Header Section */}
      <ImageBackground
        source={{
          uri: "https://cdn.jsdelivr.net/gh/RdjcMada/GessWhoStatic@main/img/curved-images/curved14.jpg",
        }}
        style={styles.headerContainer}
      >
        <LinearGradient
          colors={["rgba(58,65,110,255)", "rgba(25,28,48,255)"]}
          style={styles.headerOverlay}
        >
          <View style={styles.logoContainer}>
            <Svg width={128} height={128} viewBox="0 0 16 16" fill="#fff">
              <Path d="M5.5 9.511c.076.954.83 1.697 2.182 1.785V12h.6v-.709c1.4-.098 2.218-.846 2.218-1.932 0-.987-.626-1.496-1.745-1.76l-.473-.112V5.57c.6.068.982.396 1.074.85h1.052c-.076-.919-.864-1.638-2.126-1.716V4h-.6v.719c-1.195.117-2.01.836-2.01 1.853 0 .9.606 1.472 1.613 1.707l.397.098v2.034c-.615-.093-1.022-.43-1.114-.9zm2.177-2.166c-.59-.137-.91-.416-.91-.836 0-.47.345-.822.915-.925v1.76h-.005zm.692 1.193c.717.166 1.048.435 1.048.91 0 .542-.412.914-1.135.982V8.518z" />
              <Path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16" />
              <Path d="M8 13.5a5.5 5.5 0 1 1 0-11 5.5 5.5 0 0 1 0 11m0 .5A6 6 0 1 0 8 2a6 6 0 0 0 0 12" />
            </Svg>
          </View>

          <Text style={styles.headerTitle}>Bienvenue !</Text>
          <Text style={styles.headerSubtitle}>
            Veuillez vous connecter pour continuer
          </Text>
        </LinearGradient>
      </ImageBackground>

      {/* Login Form */}
      <View style={styles.formContainer}>
        <View style={styles.card}>
          <TextInput
            style={styles.input}
            placeholder="Adresse email"
            placeholderTextColor="#666"
            value={email}
            onChangeText={setEmail}
            keyboardType="email-address"
            autoCapitalize="none"
          />

          <TextInput
            style={styles.input}
            placeholder="Mot de passe"
            placeholderTextColor="#666"
            value={password}
            onChangeText={setPassword}
            secureTextEntry
            autoCapitalize="none"
          />

          <TouchableOpacity
            style={styles.button}
            onPress={handleLogin}
            disabled={loading}
          >
            <Text style={styles.buttonText}>Se connecter</Text>
          </TouchableOpacity>

          <TouchableOpacity
            style={styles.signupLink}
            onPress={() => navigation.navigate("Signup")}
          >
            {/* <Text style={styles.signupText}>
              Vous n'avez pas encore de compte ?{" "}
              <Text style={styles.signupHighlight}>Créer un compte</Text>
            </Text> */}
          </TouchableOpacity>
        </View>
      </View>

      {/* Footer */}
      <View style={styles.footer}>
        <Text style={styles.footerText}>
          ITUniversity : {new Date().getFullYear()}
        </Text>
      </View>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  scrollContainer: {
    flexGrow: 1,
    backgroundColor: "#f8f9fa",
  },
  headerContainer: {
    height: 350,
  },
  headerOverlay: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    padding: 20,
  },
  logoContainer: {
    marginBottom: 20,
  },
  headerTitle: {
    // fontSize: 32,
    color: "white",
    fontWeight: "bold",
    marginBottom: 10,
    fontFamily: "OpenSans-Bold",
  },
  headerSubtitle: {
    // fontSize: 16,
    color: "rgba(255,255,255,0.8)",
    textAlign: "center",
    fontFamily: "OpenSans-Regular",
  },
  formContainer: {
    paddingHorizontal: 25,
    marginTop: -50,
  },
  card: {
    backgroundColor: "white",
    borderRadius: 20,
    padding: 25,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.1,
    shadowRadius: 10,
    elevation: 5,
  },
  input: {
    height: 50,
    borderWidth: 1,
    borderColor: "#e9ecef",
    borderRadius: 12,
    paddingHorizontal: 15,
    marginBottom: 20,
    // fontSize: 16,
    backgroundColor: "#f8f9fa",
  },
  button: {
    backgroundColor: "#39406e",
    borderRadius: 12,
    height: 50,
    justifyContent: "center",
    alignItems: "center",
    marginVertical: 10,
  },
  buttonText: {
    color: "white",
    // fontSize: 16,
    fontWeight: "600",
  },
  signupLink: {
    marginTop: 20,
    alignItems: "center",
  },

  signupHighlight: {
    color: "#2d2d2d",
    fontWeight: "600",
  },
  footer: {
    marginTop: 30,
    padding: 20,
    alignItems: "center",
  },
  footerText: {
    color: "#6c757d",
    // fontSize: 12,
  },
});

export default LoginScreen;
