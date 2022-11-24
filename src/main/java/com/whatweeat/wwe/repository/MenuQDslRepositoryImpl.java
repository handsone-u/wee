package com.whatweeat.wwe.repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.whatweeat.wwe.entity.Menu;
import com.whatweeat.wwe.minigame.domain.form.FlavorName;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;

import static com.whatweeat.wwe.entity.QFlavor.flavor;
import static com.whatweeat.wwe.entity.QMenu.menu;
import static com.whatweeat.wwe.entity.QMiniGameV0.miniGameV0;
import static com.whatweeat.wwe.entity.QNation.nation;

public class MenuQDslRepositoryImpl {
    private final JPAQueryFactory jpaQueryFactory;

    public MenuQDslRepositoryImpl(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    public List<Menu> findAllFlavorNameNotIn(Collection<FlavorName> flavorNames) {
        return jpaQueryFactory.select(menu)
                .from(menu)
                .where(menu.notIn(JPAExpressions.select(menu)
                        .from(menu)
                        .innerJoin(menu.miniGameV0, miniGameV0)
                        .innerJoin(miniGameV0.flavors, flavor)
                        .where(flavor.flavorName.in(flavorNames))))
                .innerJoin(menu.miniGameV0, miniGameV0).fetchJoin()
                .innerJoin(miniGameV0.flavors, flavor).fetchJoin()
                .innerJoin(miniGameV0.nations, nation).fetchJoin()
                .distinct()
                .fetch();
    }
}
