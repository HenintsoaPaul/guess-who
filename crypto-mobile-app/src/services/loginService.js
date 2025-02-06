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
            console.log(data[0])
            if (data.length > 0) {
              response = data[0];
            }
        })
        .catch((error) => {
            console.error("Error data in:", error);
        });
    } catch (error) {
        
    }

    return response;
}

export async function logInWithMailAndPassword(email,password){
    const user = await findAccountByMail(email)
    if(user === null || user === undefined){
        return null;
    }
    return user;
}