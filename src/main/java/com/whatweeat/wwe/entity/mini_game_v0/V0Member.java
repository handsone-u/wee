package com.whatweeat.wwe.entity.mini_game_v0;

import com.whatweeat.wwe.entity.BaseEntity;
import com.whatweeat.wwe.entity.enums.ExpenseName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor @Getter @Setter
public class V0Member extends BaseEntity {
    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String token;

    private Boolean complete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GROUP_ID")
    private V0Group group;

    private Boolean rice;
    private Boolean noodle;
    private Boolean soup;
    private Boolean healthy;
    private Boolean instant;
    private Boolean alcohol;

    @Enumerated
    private ExpenseName expenseName;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private final Set<V0Flavor> flavors = new HashSet<>();

    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private final Set<V0Exclude> excludes = new HashSet<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private final Set<V0Nation> nations = new HashSet<>();

    public void addFlavor(V0Flavor flavor) {
        this.flavors.add(flavor);
        flavor.setMember(this);
    }
    public void addExclude(V0Exclude exclude) {
        this.excludes.add(exclude);
        exclude.setMember(this);
    }
    public void addNation(V0Nation nation) {
        this.nations.add(nation);
        nation.setMember(this);
    }

    public V0Member(String token, V0Group group) {
        this(token, false, group);
    }

    public V0Member(String token, Boolean complete, V0Group group) {
        this.token = token;
        this.complete = complete;
        this.group = group;
    }

    public V0Member(String token, Boolean complete, V0Group group, Boolean rice, Boolean noodle, Boolean soup,
                    Boolean healthy, Boolean instant, Boolean alcohol, ExpenseName expenseName) {
        this(token, complete, group);
        this.rice = rice;
        this.noodle = noodle;
        this.soup = soup;
        this.healthy = healthy;
        this.instant = instant;
        this.alcohol = alcohol;
        this.expenseName = expenseName;
    }
}
