package com.whatweeat.wwe.entity;

import com.whatweeat.wwe.entity.enums.ExcludeName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) @Getter
public class ExcludeFilter {
    @Id @GeneratedValue
    @Column(name = "EXCLUDE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MINI_V0_ID")
    private MiniGameV0 miniGameV0;

    @Enumerated(EnumType.STRING)
    @Column(name = "EXCLUDE_NAME")
    private ExcludeName excludeName;

    public ExcludeFilter(MiniGameV0 miniGameV0, ExcludeName excludeName) {
        this.miniGameV0 = miniGameV0;
        this.excludeName = excludeName;
    }
}
