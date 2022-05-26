package com.whatweeat.wwe.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter @Setter
public class MiniGameGroup extends BaseEntity {
    @Id
    @Column(name = "GROUP_ID")
    private Integer id;

    @OneToMany(mappedBy = "miniGameGroup", cascade = CascadeType.ALL)
    List<MiniGameMember> members = new ArrayList<>();

    public MiniGameGroup(Integer id) {
        this.id = id;
    }
}
