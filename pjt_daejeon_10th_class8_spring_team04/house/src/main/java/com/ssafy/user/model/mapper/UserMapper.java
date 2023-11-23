package com.ssafy.user.model.mapper;

import com.ssafy.user.model.UserDto;
import org.apache.ibatis.annotations.*;

import java.sql.SQLException;
import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select userId, userPw, userName from user")
    List<UserDto> selectAllUsers() throws SQLException;

    @Insert("insert into user(userId, userPw, userName) values(#{userId}, #{userPw}, #{userName})")
    int insertUser(UserDto user) throws SQLException;

    @Update("update user set userPw = #{userPw}, username = #{userName} where userId = #{userId}")
    int updateUser(UserDto user) throws SQLException;

    @Delete("delete from user where userId = #{userId}")
    int deleteUserById(String userId) throws SQLException;

    @Select("select userId, userPw, userName from user where userId = #{userId}")
    UserDto selectUserById(String userId) throws SQLException;

    @Select("select salt from salt where uid = (select uid from user where userId = #{userId})")
    String getSalt(String userId);

    @Insert("insert into salt (uid, salt) values ((select uid from user where userId = #{userId}), #{salt})")
    int setSalt(String userId, String salt);
}
