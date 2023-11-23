package com.ssafy.interceptor;

import com.ssafy.user.model.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class BeforeLoginInterceptor implements HandlerInterceptor {
    public BeforeLoginInterceptor() {
    }

    /**
     * TODO:
     * com.ssafy.config.WebMvcConfiguration에
     * BeforeLoginInterceptor을 추가하는 레지스트리에 적용할 경로를 적어주어야 함
     */
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        /**
         * 권한 없을 때 걸러야 하는 요청
         * GET /logout
         * POST /user
         * /board
         */

        HttpSession session = req.getSession();
        UserDto user = (UserDto) session.getAttribute("userInfo");

        final String requestUrl = req.getServletPath();

        if (user == null) {
            if (requestUrl.equals("/logout") || !req.getMethod().equals("GET")) {
                res.setStatus(HttpStatus.UNAUTHORIZED.value());
                return false;
            }
        }

        if (session.getAttribute("userInfo") == null) {
            res.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
        return true;
    }
}
