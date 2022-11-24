package com.whatweeat.wwe.minigame.domain.form;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Options {

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

    public static Options of(Boolean rice, Boolean noodle, Boolean soup, Boolean hangover,
                   Boolean greasy, Boolean health, Boolean alcohol, Boolean instant,
                   Boolean spicy, Boolean rich) {
        return new Options(rice, noodle, soup, hangover,
                greasy, health, alcohol, instant,
                spicy, rich);
    }
}
