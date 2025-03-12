// firebase-messaging-sw.js 서비스워커는 백그라운드에서 실행되는 스크립트로,
// 서비스를 실행하지 않거나 브라우저가 닫혔을 때 등에도 푸시 알림을 받을 수 있도록 한다

importScripts("https://www.gstatic.com/firebasejs/8.10.0/firebase-app.js");
importScripts("https://www.gstatic.com/firebasejs/8.10.0/firebase-messaging.js");

// Firebase 설정
const firebaseConfig = {
    apiKey: "YOUR_API_KEY",
    authDomain: "YOUR_AUTH_DOMAIN",
    projectId: "YOUR_PROJECT_ID",
    storageBucket: "YOUR_STORAGE_BUCKET",
    messagingSenderId: "YOUR_MESSAGING_SENDER_ID",
    appId: "YOUR_APP_ID",
    measurementId: "YOUR_MEASUREMENT_ID"
};

// Firebase 앱 초기화
const app = firebase.initializeApp(firebaseConfig);
const messaging = firebase.messaging();
