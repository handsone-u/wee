package com.whatweeat.wwe.repository;

import com.whatweeat.wwe.entity.Menu;
import com.whatweeat.wwe.minigame.domain.form.FlavorName;

import java.util.Collection;
import java.util.List;

public interface MenuQDslRepository {
    List<Menu> findAllFlavorNameNotIn(Collection<FlavorName> flavorNames);
}
