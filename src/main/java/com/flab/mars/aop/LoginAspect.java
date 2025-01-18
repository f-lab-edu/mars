package com.flab.mars.aop;

import com.flab.mars.domain.vo.MemberInfoVO;
import com.flab.mars.exception.AuthException;
import com.flab.mars.support.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 *  @Aspect : 공통적인 기능을 Aspect 로 사용하겠다고 명시하는 어노테이션
 *  @Component : 스프링 컨테이너에 빈 등록
 */
@Aspect
@Component
@Log4j2
public class LoginAspect {

    /**
     * @Before : 해당 Aspect 어노테이션이 메서드가 실행되기 전 Interceptor 와 같이 동작
     * @annotation : 결합점의 대상 객체가 주어진 어노테이션을 갖는 결합점
     * @param joinPoint
     */
    @Before("@annotation(com.flab.mars.annotation.LoginCheck)")
    public void loginCheck(JoinPoint joinPoint) {
        log.debug(" AOP - LoginCheck");

        //스프링에서 HttpSession 객체 가져오기
        checkSession();

    }

    @Before("@annotation(com.flab.mars.annotation.AccessTokenCheck)")
    public void accessTokenCheck(JoinPoint joinPoint) {

        log.debug(" AOP - accessTokenCheck");

        //스프링에서 HttpSession 객체 가져오기
        MemberInfoVO sessionLoginUser = checkSession();

        if(sessionLoginUser.getAccessToken() == null) {
            throw new AuthException("로그인에 실패했습니다. ACCESS 토큰을 가져올 수 없습니다.");
        }

    }

    private MemberInfoVO checkSession() {
        HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest().getSession(false);

        if (session == null) {
            throw new AuthException("세션이 존재하지 않습니다. 다시 로그인해주세요.");
        }

        MemberInfoVO loginUser = SessionUtil.getSessionLoginUser(session);
        if(loginUser == null ) {
            throw new AuthException("세션이 만료되었습니다. 다시 로그인해주세요.");
        }

        return loginUser;
    }
}