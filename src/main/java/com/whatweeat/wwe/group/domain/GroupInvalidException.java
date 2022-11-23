package com.whatweeat.wwe.group.domain;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GroupInvalidException extends RuntimeException {
    public GroupInvalidException(String message) {
        super(message);
    }

    public GroupInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}
