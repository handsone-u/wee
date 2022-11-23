package com.whatweeat.wwe.group.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.concurrent.ThreadLocalRandom;

public interface GroupRepository extends JpaRepository<Group, GroupPinNumber> {
    int MAX_ITERATING_COUNT = 10;

    default GroupPinNumber generateNextNumber() {
        // TODO : 핀 번호 여유가 없는 경우, 이벤트 발생하도록
        int randomNo;
        int count = 0;
        do {
            randomNo = ThreadLocalRandom.current().nextInt(1, 10000);
        } while (this.findById(GroupPinNumber.of(randomNo)).isPresent() && count++ < MAX_ITERATING_COUNT);

        if (count >= MAX_ITERATING_COUNT) {
            // TODO : 최대 반복 횟수 초과 예외 발생
            throw new RuntimeException();
        }

        return GroupPinNumber.of(randomNo);
    }
}
