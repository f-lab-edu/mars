package com.flab.mars.domain.component;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FirebaseMessageSender {

    private final FirebaseMessaging firebaseMessaging;

    public static Message makeMessage(String targetToken, String title, String body) {
        Notification notification = Notification
                .builder()
                .setTitle(title)
                .setBody(body)
                .build();
        return Message
                .builder()
                .setNotification(notification)
                .setToken(targetToken)
                .build();
    }

    public void sendPush(String token, String title, String body) {
        try {
            firebaseMessaging.send(makeMessage(token, title, body));
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
