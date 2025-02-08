// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAuth} from "firebase/auth";
import { getFirestore ,collection,getDocs, query} from "firebase/firestore";

const firebaseConfig = {
  apiKey: "AIzaSyDEk0CkCtDN4Zd77dMAUykYdFSi0iflE_8",
  authDomain: "atlasian-6d4b6.firebaseapp.com",
  projectId: "atlasian-6d4b6",
  storageBucket: "atlasian-6d4b6.firebasestorage.app",
  messagingSenderId: "767704329772",
  appId: "1:767704329772:web:ac31ab757f4c12b2e7ab89"
};

export const FIREBASE_APP = initializeApp(firebaseConfig);
export const FIREBASE_AUTH = getAuth(FIREBASE_APP);
export const FIRESTORE_DB = getFirestore(FIREBASE_APP);

export const firebaseCollection = function( collectionName ){
  const coll = collection(FIRESTORE_DB, collectionName);
  return coll;
}

export const fetchDataFromFirebase = async (collectionName, queryBuilder) => {
  try {
    const fcollection = firebaseCollection(collectionName);
    let q = fcollection;
    if (queryBuilder !== null) {
      q = queryBuilder(fcollection);
    }
    return await getDocs(q)
      .then(querySnapshot => {
        if (!querySnapshot.empty) {
          return querySnapshot.docs.map(doc => ({
            docId: doc.id,
            ...doc.data()
          }));
        } else {
          return [];
        }
      })
      .catch(error => {
        throw error;
      });
  } catch (error) {
    throw new Error("Error fetching data from Firebase: ", error);
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
