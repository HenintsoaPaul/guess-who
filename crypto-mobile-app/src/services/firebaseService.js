// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAuth} from "firebase/auth";
import { getFirestore ,collection} from "firebase/firestore";

const firebaseConfig = {
  apiKey: "AIzaSyCB8WP6g1VVBaK3MO85NloEOpWm6jl5fvI",
  authDomain: "tirelir-app-71721.firebaseapp.com",
  projectId: "tirelir-app-71721",
  storageBucket: "tirelir-app-71721.firebasestorage.app",
  messagingSenderId: "455716297248",
  appId: "1:455716297248:web:80c60491595ae9d8bf3a45",
  measurementId: "G-L2H4J2GDHW"
};

export const FIREBASE_APP = initializeApp(firebaseConfig);
export const FIREBASE_AUTH = getAuth(FIREBASE_APP);
export const FIRESTORE_DB = getFirestore(FIREBASE_APP);


export default function getCollection({collectionname}){
  const coll = collection(FIRESTORE_DB,collectionname);
  return coll;
}

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
