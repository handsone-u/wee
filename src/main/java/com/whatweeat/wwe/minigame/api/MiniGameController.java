package com.whatweeat.wwe.minigame.api;

import com.whatweeat.wwe.minigame.application.MiniGameCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/group")
public class MiniGameController {

    private final MiniGameCommandService miniGameCommandService;


}
