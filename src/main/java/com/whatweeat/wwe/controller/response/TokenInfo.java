package com.whatweeat.wwe.controller.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class TokenInfo {
    String id;
    String expires_in;
    String app_id;
}
