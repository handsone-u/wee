package com.whatweeat.wwe.controller.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class KakaoProfile {
    private String nickname;
    private String thumbnail_image_url;
    private String profile_image_url;
    private Boolean isis_default_image;
}
