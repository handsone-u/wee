package com.whatweeat.wwe.repository;

import com.whatweeat.wwe.entity.Flavor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlavorRepository extends JpaRepository<Flavor, Long> {
}
