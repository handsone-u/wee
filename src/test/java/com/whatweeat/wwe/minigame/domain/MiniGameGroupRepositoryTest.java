package com.whatweeat.wwe.minigame.domain;

import com.whatweeat.wwe.group.domain.GroupPinNumber;
import com.whatweeat.wwe.member.domain.MemberToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("h2")
@Rollback(value = false)
class MiniGameGroupRepositoryTest {
    @Autowired
    EntityManager entityManager;
    @Autowired
    MiniGameRepository repository;

    @Test
    void saveMiniGameForm() {
        int pinNumber = 1234;
        String hostToken = "host";
        String member1Token = "member1";
        GroupPinNumber groupPinNumber = GroupPinNumber.of(pinNumber);
        MemberToken host = MemberToken.of(hostToken);
        MemberToken member = MemberToken.of(member1Token);
        // GIVEN
        MiniGameGroup miniGameGroup0 = new MiniGameGroup(groupPinNumber, host);
        MiniGameMemberId miniGameMemberId0 = MiniGameMemberId.of(groupPinNumber, host);
        MiniGameMemberId miniGameMemberId1 = MiniGameMemberId.of(groupPinNumber, member);

        MiniGameMember miniGameMember0 = new MiniGameMember(miniGameMemberId0, null);
        miniGameGroup0.addMiniGameMember(miniGameMember0);

        MiniGameMember miniGameMember1 = new MiniGameMember(miniGameMemberId1, null);
        miniGameGroup0.addMiniGameMember(miniGameMember1);
        // WHEN
        MiniGameGroup saved = repository.save(miniGameGroup0);
        // THEN
        assertThat(saved.getMiniGameMembers().size()).isEqualTo(2);
        assertThat(saved.getMiniGameMembers()).contains(miniGameMember0, miniGameMember1);
    }

    @Test
    void saveMiniGameRecommendMenu() {
        int pinNumber = 1234;
        String hostToken = "host";
        GroupPinNumber groupPinNumber = GroupPinNumber.of(pinNumber);
        MemberToken host = MemberToken.of(hostToken);

        String menuName = "menu1";
        List<String> keywords = new ArrayList<>();
        String key1 = "key1";
        String key2 = "key2";
        keywords.add(key1);
        keywords.add(key2);
        // GIVEN
        MiniGameGroup miniGameGroup = new MiniGameGroup(groupPinNumber, host);
        RecommendMenu recommendMenu = new RecommendMenu(menuName, null, 1D, keywords);
        miniGameGroup.addMiniGameMenu(recommendMenu);
        // WHEN
        MiniGameGroup saved = repository.save(miniGameGroup);
        // THEN
        for (RecommendMenu menu : saved.getRecommendMenus()) {
            assertThat(menu.getKeywords()).containsAll(keywords);
        }
    }
}