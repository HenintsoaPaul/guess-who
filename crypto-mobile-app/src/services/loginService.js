import { fetchDataFromFirebase } from "./firebaseService";
import { query, where} from "firebase/firestore";

export async function findAccountByMail(email) {

    const usersData = await fetchDataFromFirebase(
        'account',
        (accountCollection) =>{
            return query(accountCollection, where('email', '==', email));
        }
    );
    console.log(usersData);
    
    if (usersData.length === 0) {
        return null;
    }
    return usersData[0];
}

export async function logInWithMailAndPassword(email,password){
    try {
        const user = await findAccountByMail(email)
        if(user === null || user === undefined){
            throw new Error("Authetification failed")
        }
        if (user.password === password) {
            return user;
        }   
        throw new Error("Authetification failed")
    } catch (error) {
        throw error;
    }
}