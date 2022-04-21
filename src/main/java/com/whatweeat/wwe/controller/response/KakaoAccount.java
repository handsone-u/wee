package com.whatweeat.wwe.controller.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
public class KakaoAccount {
    private Boolean profile_needs_agreement;
    private KakaoProfile profile;
    private Boolean name_needs_agreement;
    private String name;
    private Boolean email_needs_agreement;
    private Boolean is_email_valid;
    private Boolean is_email_verified;
    private String email;
    private Boolean age_range_needs_agreement;
    private String age_range;
    private Boolean birthday_needs_agreement;
    private String birthday;
    private Boolean gender_needs_agreement;
    private String gender;
}
