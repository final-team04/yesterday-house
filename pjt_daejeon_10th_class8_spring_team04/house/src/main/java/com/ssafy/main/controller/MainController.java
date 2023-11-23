package com.ssafy.main.controller;

import com.ssafy.user.model.UserDto;
import com.ssafy.user.model.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import static com.ssafy.util.ResponseMessage.WRONG_ID;
import static com.ssafy.util.ResponseMessage.WRONG_PASSWORD;

@RestController
@RequestMapping("/")
//@CrossOrigin("*")
public class MainController {

    private final UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    //    @PostMapping(value = "/login", consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody UserDto user, HttpSession session) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException {
        UserDto loginUser = userService.getUserById(user.getUserId());

        // 로그인 실패
        if (loginUser != null) {
            String hashingPw = userService.getHashingPw(user);

            if (!loginUser.getUserPw().equals(hashingPw)) {
                return new ResponseEntity<String>(WRONG_PASSWORD, HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<String>(WRONG_ID, HttpStatus.UNAUTHORIZED);
        }

        loginUser.setUserPw("");
        session.setAttribute("userInfo", loginUser);
        return new ResponseEntity<UserDto>(loginUser, HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.removeAttribute("userInfo");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
