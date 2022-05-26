package com.whatweeat.wwe.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor @Getter
public class MiniGameMember extends BaseEntity{
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String token;

    private Boolean complete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID")
    private MiniGameGroup miniGameGroup;

    @Embedded
    private MiniGameResultV0 miniGameResultV0;

    public MiniGameMember(String token, MiniGameGroup miniGameGroup, MiniGameResultV0 miniGameResultV0) {
        this.token = token;
        this.complete = false;
        this.miniGameGroup = miniGameGroup;
        this.miniGameResultV0 = miniGameResultV0;
    }
}
