package com.ssafy.interceptor;

import com.ssafy.user.model.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class NotAuthorizedInterceptor implements HandlerInterceptor {

    public NotAuthorizedInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        HttpSession session = req.getSession();
        UserDto user = (UserDto) session.getAttribute("userInfo");
        final String requestUrl = req.getServletPath();
        final String requestMethod = req.getMethod().toUpperCase();

        // 로그인 안됐는데 수정하려하면 거르고 로그아웃 요청을 날리면 거르기


        /* 로그인 안 했을 때 막을 요청
         GET /logout
         PUT, DELETE *
         POST * (/login, /user 제외)
         */
        if (user == null) {
            if (requestMethod.matches("PUT|DELETE") ||
                    requestMethod.equals("GET") && requestUrl.equals("/logout") ||
                    requestMethod.equals("POST") && !requestUrl.matches("/login|/user")
            ) {
                res.setStatus(HttpStatus.UNAUTHORIZED.value());
                return false;
            }
        }
        /* 로그인 했을 때 막을 요청
            POST /login, /user
         */
        else {
            if (requestMethod.equals("POST") && requestUrl.matches("/login|/user")) {
                res.setStatus(HttpStatus.UNAUTHORIZED.value());
                return false;
            }
        }
        return true;
    }
}
