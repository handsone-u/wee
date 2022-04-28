package com.whatweeat.wwe.controller;

import com.whatweeat.wwe.dto.MenuDto;
import com.whatweeat.wwe.dto.MenuPoint;
import com.whatweeat.wwe.dto.MiniGameDto;
import com.whatweeat.wwe.entity.Menu;
import com.whatweeat.wwe.service.MenuSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j @RequiredArgsConstructor
@RestController
@RequestMapping("mini")
public class MiniGameController {
    private final MenuSearchService menuSearchService;

    @PostMapping("/v1")
    public ResponseEntity<List<MenuPoint>> searchModelV1(@RequestBody MenuDto menuDto) {
        List<MenuPoint> result = menuSearchService.searchModel1(menuDto);

        Collections.sort(result);
        result = result.stream()
                .limit(10)
                .collect(Collectors.toList());

        return ResponseEntity.of(Optional.of(result));
    }
}
