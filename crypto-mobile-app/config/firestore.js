import { initializeApp } from 'firebase/app';
import { getFirestore } from 'firebase/firestore';

const firebaseConfig = {
  apiKey: "AIzaSyC87g2bzZlnvjX4VRqaB3eWLhRd3EcnGd8",
  authDomain: "tirelir-app-71721.firebaseapp.com",
  projectId: "tirelir-app-71721",
  storageBucket: "tirelir-app-71721.appspot.com",
  messagingSenderId: "455716297248",
  appId: "1:455716297248:android:5941ba6c5378052ebf3a45"
};


const app = initializeApp(firebaseConfig);
const db = getFirestore(app);

export { db, app };