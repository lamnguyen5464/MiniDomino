import * as firebase from 'firebase';

var firebaseConfig = {
    apiKey: "AIzaSyDENmnSZxCcwsRuqlJm3ZAi-r6K5ELdi0E",
    authDomain: "dominosliding.firebaseapp.com",
    databaseURL: "https://dominosliding.firebaseio.com",
    projectId: "dominosliding",
    storageBucket: "dominosliding.appspot.com",
    messagingSenderId: "737973868201",
    appId: "1:737973868201:web:2230e661fe825511407bb8",
    measurementId: "G-E64QK91L81"
  }; 
 
 export const fireBase =  firebase.initializeApp(firebaseConfig);
