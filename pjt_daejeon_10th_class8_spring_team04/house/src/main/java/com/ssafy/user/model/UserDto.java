package com.ssafy.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements Serializable {
    int uid;
    String userId;
    String userPw;
    String userName;

} 