package com.whatweeat.wwe.minigame.application;

import com.whatweeat.wwe.minigame.domain.form.NationName;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data @Builder
public class MiniGameAnswer {
    private Boolean rice;
    private Boolean noodle;
    private Boolean soup;
    private Boolean hangover;
    private Boolean greasy;
    private Boolean health;
    private Boolean alcohol;
    private Boolean instant;
    private Boolean spicy;
    private Boolean rich;
    private final Set<NationName> nation = new HashSet<>();
}
