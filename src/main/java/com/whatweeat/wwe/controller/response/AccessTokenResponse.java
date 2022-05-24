package com.whatweeat.wwe.controller.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class AccessTokenResponse {
    String token_type;
    String access_token;
    Integer expires_in;
    String refresh_token;
    Integer refresh_token_expires_in;
}
