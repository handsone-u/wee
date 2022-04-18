package com.whatweeat.wee.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor @Data
@PropertySource("classpath:kakao.properties")
public class KakaoConfig {
    private final Environment env;

    private String restKey;
    private String redirect;
    private String oauthBase;
    private String apiBase;
    private String oauthUri;
    private String oauthAuthorise;
    private String oauthCsec;
    private String apiLogout;
    private String apiTokenInfo;

    @PostConstruct
    public void initValue() {
        restKey = env.getProperty("restKey");
        redirect = env.getProperty("redirect");
        oauthBase = env.getProperty("base.oauth");
        apiBase = env.getProperty("base.api");
        oauthUri = env.getProperty("oauth.uri");
        oauthAuthorise = env.getProperty("oauth.authorise");
        oauthCsec = env.getProperty("oauth.csec");
        apiLogout = env.getProperty("api.logout");
        apiTokenInfo = env.getProperty("api.tokenInfo");

        System.out.println("this = " + this);
    }

    public boolean hasText() {
        return StringUtils.hasText(restKey)
                && StringUtils.hasText(redirect)
                && StringUtils.hasText(oauthBase)
                && StringUtils.hasText(apiBase)
                && StringUtils.hasText(oauthUri)
                && StringUtils.hasText(oauthAuthorise)
                && StringUtils.hasText(oauthCsec)
                && StringUtils.hasText(apiLogout)
                && StringUtils.hasText(apiTokenInfo);
    }
}
