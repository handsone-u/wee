package com.whatweeat.wee.controller.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class UserInfo {
    private Long id;
    private KakaoAccount kakao_account;
    private KakaoProperty properties;
}
