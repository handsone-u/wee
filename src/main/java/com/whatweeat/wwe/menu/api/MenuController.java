package com.whatweeat.wwe.menu.api;

import com.whatweeat.wwe.menu.domain.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuRepository menuRepository;


}
