package com.whatweeat.wwe.controller;

import com.whatweeat.wwe.config.KakaoConfig;
import com.whatweeat.wwe.controller.request.AccessTokenRequest;
import com.whatweeat.wwe.controller.response.AccessTokenResponse;
import com.whatweeat.wwe.controller.response.TokenInfo;
import com.whatweeat.wwe.controller.response.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/kakao")
public class KakaoController {
    private final KakaoConfig kakaoConfig;
    private final WebClient oauthWebClient;
    private final WebClient apiWebClient;

    @GetMapping("/login")
    public void login(HttpServletResponse response) throws IOException {
        log.info("LOGIN");
        String uri = UriComponentsBuilder
                .fromPath(kakaoConfig.getOauthBase())
                .pathSegment(kakaoConfig.getOauthAuthorise())
                .queryParam("response_type", "code")
                .queryParam("client_id", kakaoConfig.getRestKey())
                .queryParam("redirect_uri", kakaoConfig.getRedirect())
                .build()
                .toUriString();
        log.info("KAKAO OAUTH URI : {}", uri);

        response.sendRedirect(uri);
    }

    @GetMapping("/logout")
    public String logout(@RequestParam String accessToken) {
        log.info(accessToken);

        return apiWebClient.get()
                .uri(kakaoConfig.getApiLogout())
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse -> clientResponse.bodyToMono(String.class)
                        .flatMap(error -> Mono.error(new RuntimeException(error))))
                .bodyToMono(String.class)
                .block(Duration.ofMillis(1000));
    }

    @GetMapping("/oauth")
    public TokenInfo authorization(String code, String state, String error,
                                   @RequestParam(name = "error_description", required = false) String desc) {
        AccessTokenRequest request = new AccessTokenRequest("authorization_code",
                kakaoConfig.getRestKey(), kakaoConfig.getRedirect(), code, kakaoConfig.getOauthCsec());

        AccessTokenResponse response = oauthWebClient
                .post()
                .uri(kakaoConfig.getOauthUri())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(BodyInserters
                        .fromFormData("grant_type", request.getGrant_type())
                        .with("client_id", request.getClient_id())
                        .with("redirect_uri", request.getRedirect_uri())
                        .with("code", request.getCode())
                        .with("client_secret", request.getClient_secret()))
                .retrieve()
                .onStatus(HttpStatus::isError,
                        r -> r.bodyToMono(String.class) // error body as String or other class
                                .flatMap(errorResponse -> Mono.error(new RuntimeException(errorResponse))))
                .bodyToMono(AccessTokenResponse.class)
                .block(Duration.ofMillis(1000));

        log.info("ACCESS TOKEN = [{}]", response);

        return apiWebClient
                .get()
                .uri(kakaoConfig.getApiTokenInfo())
                .header("Authorization", "Bearer " + response.getAccess_token())
                .retrieve()
                .onStatus(HttpStatus::isError,
                        r -> r.bodyToMono(String.class) // error body as String or other class
                                .flatMap(errorResponse -> Mono.error(new RuntimeException(errorResponse))))
                .bodyToMono(TokenInfo.class)
                .block(Duration.ofMillis(1000));
    }

    @GetMapping("/user")
    public UserInfo userInfo(@RequestParam String accessToken) {
        UserInfo userInfo = apiWebClient
                .get()
                .uri(kakaoConfig.getApiUser())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .header("Authorization", "Bearer " + accessToken)
                .retrieve()
                .onStatus(HttpStatus::isError, clientResponse -> clientResponse.bodyToMono(String.class)
                        .flatMap(error -> Mono.error(new RuntimeException(error))))
                .bodyToMono(UserInfo.class)
                .block(Duration.ofMillis(1000));

        log.info("USER = [{}]", userInfo);
        return userInfo;

    }
}
