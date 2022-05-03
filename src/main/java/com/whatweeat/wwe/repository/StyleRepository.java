package com.whatweeat.wwe.repository;

import com.whatweeat.wwe.entity.Menu;
import com.whatweeat.wwe.entity.Style;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StyleRepository extends JpaRepository<Style, Long> {
    Style findByMenu(Menu menu);

    List<Style> findByIsRice(boolean isRice);

    List<Style> findByIsSoup(boolean isSoup);

    List<Style> findByIsNoodle(boolean isNoodle);
}
