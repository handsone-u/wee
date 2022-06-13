package com.whatweeat.wwe.service.mini_game_v0;

import com.whatweeat.wwe.entity.MiniGameV0;
import com.whatweeat.wwe.entity.mini_game_v0.V0Member;

public interface MenuCalculator {
    double calculateV0(MiniGameV0 menuV0, V0Member member);
}
