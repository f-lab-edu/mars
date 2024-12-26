package com.flab.mars.support;

import com.flab.mars.domain.vo.MemberInfoVO;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {

    public static final String ROLE = "sessionUser";

    private SessionUtil() {
        throw new AssertionError("Cannot instantiate utility class");
    }


    public static void setSessionValue(HttpSession session, String key, Object value) {
        session.setAttribute(key, value);
    }

    public static MemberInfoVO getSessionLoginUser(HttpSession session) {
        return (MemberInfoVO) session.getAttribute(ROLE);
    }

}
