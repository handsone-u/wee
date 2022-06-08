package com.whatweeat.wwe.controller;

import com.whatweeat.wwe.controller.response.MenuInfo;
import com.whatweeat.wwe.dto.MenuCreateDTO;
import com.whatweeat.wwe.dto.MenuHomeResponse;
import com.whatweeat.wwe.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor @Slf4j
@RestController
@RequestMapping("/menu")
public class MenuController {
    private final MenuService menuServiceImpl;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public int addMenu(@RequestBody List<MenuCreateDTO> dtos) {
        int result = 0;
        for (MenuCreateDTO dto : dtos) {
            dto.lookup();
            menuServiceImpl.save(dto);
            result++;
        }
        return result;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MenuInfo> findAll() {
        return menuServiceImpl.findAll().stream()
                .map(MenuInfo::ofMenuInfo)
                .collect(Collectors.toList());
    }

    @GetMapping("/random")
    public ResponseEntity<MenuHomeResponse> home() {
        String menuName = "실험중";
        String menuPath = "https://cdn.imweb.me/upload/S2017032758d89cbde9730/5c344a55e5613.jpg";
        return ResponseEntity.ok(new MenuHomeResponse(menuName, menuPath));
    }
}
