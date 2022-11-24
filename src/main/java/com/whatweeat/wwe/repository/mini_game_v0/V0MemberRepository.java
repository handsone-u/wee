package com.whatweeat.wwe.repository.mini_game_v0;

import com.whatweeat.wwe.entity.mini_game_v0.V0Group;
import com.whatweeat.wwe.entity.mini_game_v0.V0Member;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

public interface V0MemberRepository {

    Optional<V0Member> findByTokenAndGroup(String token, V0Group group);

    @EntityGraph(attributePaths = {"nations", "excludes"})
    List<V0Member> findAllByGroup_id(Integer groupId);
}
