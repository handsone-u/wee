package com.whatweeat.wwe.member.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "member")
public class Member {

    @EmbeddedId
    private MemberToken memberToken;
}
