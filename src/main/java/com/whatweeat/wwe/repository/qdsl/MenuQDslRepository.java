package com.whatweeat.wwe.repository.qdsl;

import com.whatweeat.wwe.entity.Menu;
import com.whatweeat.wwe.entity.enums.ExcludeName;

import java.util.List;

public interface MenuQDslRepository {
    List<Menu> findExceptExcludeNames(List<ExcludeName> names);
}
