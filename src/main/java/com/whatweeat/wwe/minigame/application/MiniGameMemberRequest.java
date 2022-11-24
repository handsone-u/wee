package com.whatweeat.wwe.minigame.application;

import com.whatweeat.wwe.minigame.domain.form.FlavorName;
import com.whatweeat.wwe.minigame.domain.form.MiniGameForm;
import com.whatweeat.wwe.minigame.domain.form.Options;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data @Builder
public class MiniGameMemberRequest {
    private MiniGameAnswer gameAnswer;
    private String pinNumber;
    private String token;
    private final Set<FlavorName> dislikedFoods = new HashSet<>();

    public MiniGameForm toForm() {
        Options options = Options.of(gameAnswer.getRice(), gameAnswer.getNoodle(), getGameAnswer().getSoup(), gameAnswer.getHangover(),
                gameAnswer.getGreasy(), gameAnswer.getHealth(), gameAnswer.getAlcohol(), gameAnswer.getInstant(),
                gameAnswer.getSpicy(), gameAnswer.getRich());

        return MiniGameForm.of(options, dislikedFoods, gameAnswer.getNation());
    }
}
