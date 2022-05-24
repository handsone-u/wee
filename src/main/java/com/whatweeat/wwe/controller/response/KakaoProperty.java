package com.whatweeat.wwe.controller.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class KakaoProperty {
    private String nickname;
    private String thumbnail_image;
    private String profile_image;
    private String custom1;
    private String custom2;
}
