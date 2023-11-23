package com.ssafy.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/*

userId: user.id,
    newUserName: newUserName.value,
    oldUserPw: oldUserPw.value,
    newUserPw: newUserPw.value,
    newUserPwCheck: newUserPwCheck.value,

 */
public class EditUserDto {
    String userId;
    String newUserName;
    String oldUserPw;
    String newUserPw;
    String newUserPwCheck;
}
