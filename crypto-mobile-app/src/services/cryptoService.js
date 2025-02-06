import { fetchDataFromFirebase, FIREBASE_AUTH, FIRESTORE_DB} from "./firebaseService";
import { collection, query, where, getDocs } from "firebase/firestore";

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
