package com.ssafy.user.model.service;

import com.ssafy.user.model.UserDto;
import com.ssafy.user.model.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper mapper;

    @Autowired
    public UserServiceImpl(UserMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public UserDto getUserById(String userId) throws SQLException {
        return mapper.selectUserById(userId);
    }

    @Override
    public String getHashingPw(UserDto user) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // salt 가져오기
        String salt = mapper.getSalt(user.getUserId());

        // hashing
        return hashPassword(user.getUserPw(), salt);
    }
    

    @Override
    public int regist(UserDto user) throws SQLException, NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
        // salt 생성하기
        String salt = generateSalt();

        // hashing
        user.setUserPw(hashPassword(user.getUserPw(), salt));

        // 회원가입
        if (mapper.insertUser(user) == -1) return -1;

        // salt 저장
        return mapper.setSalt(user.getUserId(), salt);
    }

    @Override
    public int edit(UserDto user) throws SQLException {
        return mapper.updateUser(user);
    }

    @Override
    public int deleteUserById(String userId) throws SQLException {
        return mapper.deleteUserById(userId);
    }

    @Override
    public List<UserDto> getAllUsers() throws SQLException {
        return mapper.selectAllUsers();
    }

    @Override
    public String getSalt(String userId) throws SQLException {
        return mapper.getSalt(userId);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    @Override
    public String hashPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 10000;
        int keyLength = 256;
        char[] passwordChars = password.toCharArray();
        byte[] saltBytes = Base64.getDecoder().decode(salt);

        KeySpec spec = new PBEKeySpec(passwordChars, saltBytes, iterations, keyLength);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        byte[] hashedBytes = factory.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hashedBytes);
    }


}
