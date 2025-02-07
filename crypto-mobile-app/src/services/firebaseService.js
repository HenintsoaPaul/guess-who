// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAuth} from "firebase/auth";
import { getFirestore ,collection,getDocs} from "firebase/firestore";

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

export const firebaseCollection = function( collectionName ){
  const coll = collection(FIRESTORE_DB, collectionName);
  return coll;
}

export const fetchDataFromFirebase = (collectionName, queryBuilder, errorMarks) => {
  try {
    const fcollection = firebaseCollection(collectionName);
    let q = fcollection;
    if (queryBuilder !== null) {
      q = queryBuilder(fcollection);
    }
    return getDocs(q)
      .then(querySnapshot => {
        if (!querySnapshot.empty) {
          return querySnapshot.docs.map(doc => doc.data());
        } else {
          throw new Error("No data found: " + errorMarks);
        }
      })
      .catch(error => {
        throw error;
      });
  } catch (error) {
    console.error("Error fetching data from Firebase: ", error);
  }
};

export const extractDataFromPromise = async (promise) => {
  await promise
    .then((data)=>{
        if (data.length > 0) {
          return data;
        }
        return [];
    })
    .catch((error) => {
        throw error
    });
};
