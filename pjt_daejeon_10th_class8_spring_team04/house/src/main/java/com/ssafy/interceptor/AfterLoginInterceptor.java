package com.ssafy.interceptor;

import com.ssafy.user.model.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AfterLoginInterceptor implements HandlerInterceptor {

    public AfterLoginInterceptor() {
    }

    /**
     * TODO:
     * com.ssafy.config.WebMvcConfiguration에
     * BeforeLoginInterceptor을 추가하는 레지스트리에 적용할 경로를 적어주어야 함
     * 예를 들어 내가 쓰지 않은 게시물을 수정하려 할 때 AfterLoginInterCeptor에서 걸러내는 것이 좋다.
     */
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        HttpSession session = req.getSession();
        UserDto user = (UserDto) session.getAttribute("userInfo");

        final String requestUrl = req.getServletPath();

        if (user != null && requestUrl.equals("/login")) {
            res.setStatus(HttpStatus.FORBIDDEN.value());
            return false;
        }

        // 로직 추가

        return true;

    }
}
