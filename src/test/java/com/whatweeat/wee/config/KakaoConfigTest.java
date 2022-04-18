package com.whatweeat.config;

import com.whatweeat.wee.config.KakaoConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration(classes = KakaoConfig.class)
class KakaoConfigTest {

    @Autowired
    private KakaoConfig kakaoConfig;

    @Test
    void init() {
        assertThat(kakaoConfig).isNotNull();
        assertThat(kakaoConfig.hasText()).isTrue();
    }
}