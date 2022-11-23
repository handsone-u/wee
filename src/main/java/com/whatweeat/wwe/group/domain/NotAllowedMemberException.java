package com.whatweeat.wwe.group.domain;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotAllowedMemberException extends RuntimeException{
    public NotAllowedMemberException(String message) {
        super(message);
    }

    public NotAllowedMemberException(String message, Throwable cause) {
        super(message, cause);
    }
}
