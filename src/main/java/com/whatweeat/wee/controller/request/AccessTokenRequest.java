package com.whatweeat.wee.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
@Data
public class AccessTokenRequest {
    String grant_type;
    String client_id;
    String redirect_uri;
    String code;
    String client_secret;
}
