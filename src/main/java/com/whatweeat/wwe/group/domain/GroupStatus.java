package com.whatweeat.wwe.group.domain;

public enum GroupStatus {
    PENDING,
    RUNNING,
    DONE,
    ;

    public boolean isPending() {
        return this.equals(PENDING);
    }
}
