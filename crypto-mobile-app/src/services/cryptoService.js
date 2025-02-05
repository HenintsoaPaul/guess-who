import { use } from "react";
import { FIREBASE_AUTH, FIRESTORE_DB } from "./firebaseService";

export function getCrypto(){
    const data = [
        {
            name : 'bitcoin',
            symbol: 'BTC',
            price: '100', 
        },
        {
            name : 'ether',
            symbol: 'ETR',
            price: '200', 
        },
        {
            name : 'bitcoin',
            symbol: 'BTC',
            price: '100', 
        },
        {
            name : 'ether',
            symbol: 'ETR',
            price: '200', 
        },
        {
            name : 'bitcoin',
            symbol: 'BTC',
            price: '100', 
        },
        {
            name : 'ether',
            symbol: 'ETR',
            price: '200', 
        },
        {
            name : 'bitcoin',
            symbol: 'BTC',
            price: '100', 
        },
        {
            name : 'ether',
            symbol: 'ETR',
            price: '200', 
        },
        {
            name : 'bitcoin',
            symbol: 'BTC',
            price: '100', 
        },
        {
            name : 'ether',
            symbol: 'ETR',
            price: '200', 
        },
    ];
    return data;
}

export function getUserCryptoFavoris(){
    const user = FIREBASE_AUTH.currentUser;
    // if(user == null){
        return [];
    // }
    // Recuperer la liste des favoris de l'utilisateur conneecter
    // const favoris = getCrypto();
    // return favoris;
}

export function findUserByMail(email) {
    const firestore = FIRESTORE_DB;
    return firestore.collection('accounts').where('email', '==', email).get()
        .then(querySnapshot => {
            if (!querySnapshot.empty) {
                return querySnapshot.docs[0].data();
            } else {
                throw new Error('No user found with the provided email.');
            }
        })
        .catch(error => {
            console.error('Error finding user by email:', error);
            throw error;
        });
}