package com.whatweeat.wwe.repository.qdsl;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.whatweeat.wwe.entity.Menu;
import com.whatweeat.wwe.entity.QExcludeFilter;
import com.whatweeat.wwe.entity.QMenu;
import com.whatweeat.wwe.entity.enums.ExcludeName;

import javax.persistence.EntityManager;
import java.util.List;

import static com.whatweeat.wwe.entity.QExcludeFilter.*;
import static com.whatweeat.wwe.entity.QMenu.menu;

public class MenuQDslRepositoryImpl implements MenuQDslRepository{

    private final JPAQueryFactory queryFactory;

    public MenuQDslRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Menu> findExceptExcludeNames(List<ExcludeName> names) {
        return queryFactory.select(menu)
                .from(menu)
                .where(menu.notIn(JPAExpressions
                        .select(menu)
                        .from(menu)
                        .innerJoin(menu.excludeFilters, excludeFilter)
                        .where(excludeFilter.excludeName.in(names))
                ))
                .fetch();
    }
}
