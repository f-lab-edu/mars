package com.flab.mars.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * FCM 알림기  각 기기에 맞는 토큰 생성 필수 (임시 코드)
 */
@RequiredArgsConstructor
@Controller
@RequestMapping("/api/fcmtoken")
public class FCMTokenContoller {

    @GetMapping
    public String fcmToken() {
        return "fcmtoken";
    }

}
