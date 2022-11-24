package com.whatweeat.wwe.group.api;

import com.whatweeat.wwe.group.application.GroupService;
import com.whatweeat.wwe.group.domain.GroupPinNumber;
import com.whatweeat.wwe.minigame.application.MiniGameCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/group")
public class GroupController {

    private final GroupService groupService;
    private final MiniGameCommandService miniGameCommandService;

    @PostMapping
    public ResponseEntity<Integer> createGroup(
            @RequestParam String hostToken) {
        GroupPinNumber groupPinNumber = groupService.createGroup(hostToken);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(groupPinNumber.getPinNumber());
    }

    @DeleteMapping("/{pin}")
    public Boolean removeGroup(
            @PathVariable String pin) {
        groupService.removeGroup(Integer.parseInt(pin));
        return true;
    }

    @GetMapping("/{pin}")
    public ResponseEntity<Boolean> isValid(
            @PathVariable String pin) {
        return ResponseEntity
                .ok(groupService.isValid(Integer.parseInt(pin)));
    }
}
