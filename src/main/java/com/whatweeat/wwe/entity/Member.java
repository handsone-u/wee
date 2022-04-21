package com.whatweeat.wwe.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor @Getter
public class Member {
    @Id @GeneratedValue
    @Column(name ="MEMBER_ID")
    private Long id;

    @Column(name = "KAKAO_ID")
    private Long kakaoId;

    private String accessToken;
    private String refreshToken;
    private Integer expiresIn;
    private Integer refreshTokenExpiresIn;

    private String username;
    private String nickname;

    public Member(Long kakaoId, String accessToken, String refreshToken,
                  Integer expiresIn, Integer refreshTokenExpiresIn,
                  String username, String nickname) {
        this.kakaoId = kakaoId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
        this.username = username;
        this.nickname = nickname;
    }
}
