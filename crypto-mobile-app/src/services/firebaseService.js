// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAuth} from "firebase/auth";
import { getFirestore ,collection,getDocs, query, where} from "firebase/firestore";

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

export function cloneMobData(obj, excludedKeys = []) {
  excludedKeys.push('docId');  
  return cloneData(obj, excludedKeys);
}

export function cloneData(obj, excludedKeys = []) {
  const clonedObj = JSON.parse(JSON.stringify(obj));
  if (Array.isArray(clonedObj)) {
    return clonedObj.map(item => removeExcludedKeys(item, excludedKeys));
  } else {
    return removeExcludedKeys(clonedObj, excludedKeys);
  }
}

import { doc, getDoc, setDoc, updateDoc } from "firebase/firestore";
export const updateOrCreateMobDocWithCheck = async (baseCollection,queryCheck, mobObj, updateData) => {
    const data = fetchDataFromFirebase(baseCollection,queryCheck);
    if(data.length > 0){
      console.log("data exist");
      
      return updateOrCreateMobDoc(baseCollection,data[0],updateData);
    }
    console.log("no data fetch");
    
    return updateOrCreateMobDoc(baseCollection,mobObj,updateData);
}

export const updateOrCreateMobDoc = async (baseCollection, mobObj, updateData) => {
    const mobCollection = "mob_" + baseCollection;
    let mobRef;
    if (mobObj.docId) {
        mobRef = doc(FIRESTORE_DB, mobCollection, mobObj.docId);
    } else {
        mobRef = doc(collection(FIRESTORE_DB, mobCollection));
    }
    const accountDoc = await getDoc(mobRef);
    let mobClone = cloneMobData(mobObj);
    if (updateData !== null && updateData !== undefined) {
        Object.assign(mobClone, updateData);
    }
    if (accountDoc.exists()) {
        console.log("Existing");
        await updateDoc(mobRef, {
            ...mobClone
        });
    } else {
        console.log("Doesn't Exist");
        await setDoc(mobRef, {
            ...mobClone,
        });
    }
}


function removeExcludedKeys(obj, excludedKeys) {
  if (typeof obj !== 'object' || obj === null) {
    return obj;
  }
  return Object.keys(obj).reduce((acc, key) => {
    if (!excludedKeys.includes(key)) {
      acc[key] = obj[key];
    }
    return acc;
  }, Array.isArray(obj) ? [] : {});
}
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
