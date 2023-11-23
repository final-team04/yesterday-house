package com.ssafy.user.model.service;


import com.ssafy.user.model.UserDto;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.List;

public interface UserService {

    //    UserDto login(UserDto user) throws SQLException;
    UserDto getUserById(String userId) throws SQLException;

    int regist(UserDto user) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException;

    int edit(UserDto user) throws SQLException;

    int deleteUserById(String userId) throws SQLException;

    List<UserDto> getAllUsers() throws SQLException;

    String getSalt(String userId) throws SQLException;

    String getHashingPw(UserDto user) throws NoSuchAlgorithmException, InvalidKeySpecException;

//    String getHashingPw(String userId) throws NoSuchAlgorithmException, InvalidKeySpecException;

    String hashPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException;
}
