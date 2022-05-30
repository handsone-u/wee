package com.whatweeat.wwe.service;

import com.whatweeat.wwe.dto.MiniGameResultDTO;
import com.whatweeat.wwe.entity.mini_game_v0.V0Group;

public interface MiniGameService {
    int createGroup();

    V0Group saveResult(MiniGameResultDTO dto);

    void getGroupResult(int pin);

    void deleteGroup(int pin);

    boolean pinValidCheck(int id);

    int countMember(int id);

    int countCompleteMember(int id);
}
