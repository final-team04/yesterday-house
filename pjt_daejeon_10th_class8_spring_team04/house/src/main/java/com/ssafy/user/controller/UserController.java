package com.ssafy.user.controller;

import com.ssafy.user.model.EditUserDto;
import com.ssafy.user.model.UserDto;
import com.ssafy.user.model.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.List;

import static com.ssafy.util.ResponseMessage.UNAUTHORIZED;
import static com.ssafy.util.ResponseMessage.WRONG_PASSWORD;

@RestController
//@CrossOrigin("*")
public class UserController {
    UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserInfo(HttpSession session) throws SQLException {
        UserDto user = (UserDto) session.getAttribute("userInfo");

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> searchById(@PathVariable String userId) throws SQLException {
        System.out.println("GET /user/" + userId);
        UserDto result = service.getUserById(userId);

        if (result == null) return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<UserDto>(result, HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<?> regist(@RequestBody UserDto user, HttpSession session) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
        System.out.println("POST /user : " + user);
        int result = service.regist(user);

        if (result == -1) return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/user")
    public ResponseEntity<?> modify(@RequestBody EditUserDto user, HttpSession session) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException, NullPointerException {
        System.out.println("PUT /user : " + user);
        UserDto sessionUserInfo = (UserDto) session.getAttribute("userInfo");

        if (!user.getNewUserPw().equals(user.getNewUserPwCheck())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // 1
        String userId = user.getUserId();
        if (!user.getUserId().equals(sessionUserInfo.getUserId())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }


        // 2
        // DB에 저장된 해시된 유저 비밀번호와
        UserDto storedUser = service.getUserById(userId);
        String storedUserPw = storedUser.getUserPw();

        // 유저가 입력해온 비밀번호를 해시한 것
        String userSalt = service.getSalt(userId);
        String hashedUserPw = service.hashPassword(user.getOldUserPw(), userSalt);

        if (!storedUserPw.equals(hashedUserPw)) {
            return new ResponseEntity<>(WRONG_PASSWORD, HttpStatus.UNAUTHORIZED);
        }

        // 3
        storedUser.setUserName(user.getNewUserName());
        String HashedNewUserPw = service.hashPassword(user.getNewUserPw(), userSalt);
        storedUser.setUserPw(HashedNewUserPw);

        int result = service.edit(storedUser);
        storedUser.setUserPw("");
        session.setAttribute("userInfo", storedUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> delete(@PathVariable String userId, HttpSession session) throws SQLException {
        System.out.println("DELETE /user/" + userId);

        UserDto userInfo = (UserDto) session.getAttribute("userInfo");

        if (userInfo == null || !userId.equals(userInfo.getUserId()))
            return new ResponseEntity<>(UNAUTHORIZED, HttpStatus.UNAUTHORIZED);

        int result = service.deleteUserById(userId);
        session.invalidate();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<?> searchAllUsers() throws SQLException {
        List<UserDto> userList = service.getAllUsers();
        if (userList == null || userList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<UserDto>>(userList, HttpStatus.OK);
    }
}
