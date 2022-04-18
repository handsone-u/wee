package com.whatweeat.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor @Getter
@PropertySource("classpath:kakao.properties")
public class KakaoConfig {
    private final Environment env;

    private String restKey;
    private String redirect;
    private String oauthBase;
    private String apiBase;
    private String oauthUri;
    private String oauthCsec;

    @PostConstruct
    public void initValue() {
        restKey = env.getProperty("restKey");
        redirect = env.getProperty("redirect");
        oauthBase = env.getProperty("base.oauth");
        apiBase = env.getProperty("base.api");
        oauthUri = env.getProperty("oauth.uri");
        oauthCsec = env.getProperty("oauth.csec");
    }

    public boolean hasText() {
        return StringUtils.hasText(restKey)
                && StringUtils.hasText(redirect)
                && StringUtils.hasText(oauthBase)
                && StringUtils.hasText(apiBase)
                && StringUtils.hasText(oauthUri)
                && StringUtils.hasText(oauthCsec);
    }
}
