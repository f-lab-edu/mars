package com.flab.mars.support;

import com.flab.mars.domain.vo.TokenInfo;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {

    public static final String ROLE = "tokenInfo";

    private SessionUtil() {
        throw new AssertionError("Cannot instantiate utility class");
    }

    public static void setSessionAccessToKenValue(HttpSession session, TokenInfo tokenInfo) {
        session.setAttribute(ROLE, tokenInfo);
    }

    public static TokenInfo getSessionAccessToKenValue(HttpSession session) {
        return (TokenInfo) session.getAttribute(ROLE);
    }

}
