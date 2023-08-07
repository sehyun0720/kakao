package com.test.snslogin.kakao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class User {
    private String code;
    private String phone;
    private String id;
    private String userName;
    private String nickname;
    private String email;
    private String profileImg;
    private String ci;
    private String token;
}
