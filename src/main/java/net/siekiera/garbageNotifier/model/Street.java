package net.siekiera.garbageNotifier.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Eric on 04.02.2017.
 */
@Entity
@Table(name = "streets")
public class Street {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String name;
    @ManyToOne
    @JoinColumn(name="street_group_id")
    private StreetGroup streetGroup;
    @OneToMany(mappedBy = "street", cascade = CascadeType.ALL)
    private Set<UserInfo> userInfos;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StreetGroup getStreetGroup() {
        return streetGroup;
    }

    public void setStreetGroup(StreetGroup streetGroup) {
        this.streetGroup = streetGroup;
    }

    public Set<UserInfo> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(Set<UserInfo> userInfos) {
        this.userInfos = userInfos;
    }
}
