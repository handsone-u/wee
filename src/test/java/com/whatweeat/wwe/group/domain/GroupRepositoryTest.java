package com.whatweeat.wwe.group.domain;

import com.whatweeat.wwe.member.domain.MemberToken;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class GroupRepositoryTest {

    @PersistenceContext
    EntityManager entityManager;
    @Autowired GroupRepository repository;

    @Test
    void generatePinNumber() {
        GroupPinNumber pinNumber = repository.generateNextNumber();
        System.out.println("pinNumber = " + pinNumber);
        System.out.println("pinNumber.pinNumber = " + pinNumber.pinNumber);

        assertThat(pinNumber.pinNumber).isGreaterThan(0)
                .isLessThan(10000);
    }

    @Test
    void auditLocalDateTime() {
        // GIVEN
        String host = "host";
        GroupPinNumber groupPinNumber = GroupPinNumber.of(repository.generateNextNumber().getPinNumber());
        MemberToken memberToken = MemberToken.of(host);

        // WHEN
        Group group = new Group(groupPinNumber, memberToken);
        Group saved = repository.save(group);

        // THEN
        assertThat(saved.getCreatedDate()).isNotNull();
        System.out.println("saved.getCreatedDate() = " + saved.getCreatedDate());
    }
}