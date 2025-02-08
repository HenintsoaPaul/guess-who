import { extractDataFromPromise, fetchDataFromFirebase } from "./firebaseService";
import { query, where} from "firebase/firestore";

export async function findAccountByMail(email) {

    const promise = fetchDataFromFirebase(
        'account',
        (accountCollection) =>{
            return query(accountCollection, where('email', '==', email));
        }
        ,'No user found with the provided email'
    );
    let response = null;
    try {
        await promise
        .then((data)=>{
            console.log("USER FOUND ....");
            
            console.log(data[0])
            if (data.length > 0) {
              response = data[0];
            }
        })
        .catch((error) => {
            throw error;
        });
    } catch (error) {
        throw error;   
    }

    return response;
}

export async function logInWithMailAndPassword(email,password){
    try {
        const user = await findAccountByMail(email)
        if(user === null || user === undefined){
            return null;
        }
        if (user.password === password) {
            return user;
        }   
        throw new Error("Authetification failed , check your mails")
    } catch (error) {
        throw error;
    }
}