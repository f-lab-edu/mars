package com.flab.mars.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * firebase 는 firebase 서비스에 접근하기 위해 서비스 계정 인증이 필요함.
 * 인증 정보를 로드하여 firebase 와 통신
 */
@Slf4j
@Configuration
public class FCMConfig {

    @Value("${firebase.credentials.path}")
    private String fileResourceURL;

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        // Firebase 서비스 계정 키 파일 로드
        ClassPathResource resource = new ClassPathResource(fileResourceURL);

        // try-with-resources 사용으로 InputStream을 자동으로 닫음
        try (InputStream serviceAccount = resource.getInputStream()) {
            // FireBase 인증 옵션
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            // 현재 등록된 FirebaseApp  목록 가져오기
            List<FirebaseApp> apps = FirebaseApp.getApps();

            // 아직 초기화 되어이지 않다면 새로 생성
            if (apps.isEmpty()) {
                return FirebaseApp.initializeApp(options);
            } else {
                return apps.getFirst(); // 이미 초기화된 FirebaseApp 반환
            }
        }
    }

    @Bean
    public FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
        return FirebaseMessaging.getInstance(firebaseApp);
    }
}
