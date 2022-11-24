package com.whatweeat.wwe.group.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@NoArgsConstructor(access = PROTECTED) @AllArgsConstructor(access = PROTECTED) @Getter
public class GroupPinNumber implements Serializable {

    @Column(name = "pin_number")
    Integer pinNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupPinNumber that = (GroupPinNumber) o;
        return Objects.equals(pinNumber, that.pinNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pinNumber);
    }

    public static GroupPinNumber of(Integer pinNumber) {
        return new GroupPinNumber(pinNumber);
    }
}
