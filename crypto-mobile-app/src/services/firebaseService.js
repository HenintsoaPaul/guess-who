import firebase from "firebase/app";
import "firebase/firestore";

// Configuration Firebase
const firebaseConfig = {
    apiKey: "AIzaSyC87g2bzZlnvjX4VRqaB3eWLhRd3EcnGd8",
    authDomain: "YOUR_AUTH_DOMAIN",
    projectId: "tirelir-app-71721",
    storageBucket: "tirelir-app-71721.firebasestorage.app",
    messagingSenderId: "455716297248",
    appId: "1:455716297248:android:74971b4acd3e1986bf3a45"
};

// Initialiser Firebase
if (!firebase.apps.length) {
    firebase.initializeApp(firebaseConfig);
}

const db = firebase.firestore();

export const fetchDataFromFirebase = async () => {
    try {
        const snapshot = await db.collection("your-collection").get();
        snapshot.forEach(doc => {
            console.log(doc.id, "=>", doc.data());
        });
    } catch (error) {
        console.error("Error fetching data from Firebase: ", error);
    }
};
