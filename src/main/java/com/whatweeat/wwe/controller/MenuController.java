package com.whatweeat.wwe.controller;

import com.whatweeat.wwe.dto.MenuCreateDTO;
import com.whatweeat.wwe.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor @Slf4j
@RestController
@RequestMapping("/menu")
public class MenuController {
    private final MenuService menuService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public int addMenu(@RequestBody List<MenuCreateDTO> dtos) {
        int result = 0;
        for (MenuCreateDTO dto : dtos) {
            dto.lookup();
            menuService.save(dto);
            result++;
        }
        return result;
    }
}
