package net.siekiera.garbageNotifier.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Eric on 04.02.2017.
 */
@Entity
@Table(name = "street_groups")
public class StreetGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String nazwa;
    @OneToMany(mappedBy = "streetGroup", cascade = CascadeType.ALL)
    private Set<Street> ulice;
    @OneToMany(mappedBy = "streetGroup", cascade = CascadeType.ALL)
    private Set<GarbageCollection> garbageCollections;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Set<Street> getUlice() {
        return ulice;
    }

    public void setUlice(Set<Street> ulice) {
        this.ulice = ulice;
    }

    public Set<GarbageCollection> getGarbageCollections() {
        return garbageCollections;
    }

    public void setGarbageCollections(Set<GarbageCollection> garbageCollections) {
        this.garbageCollections = garbageCollections;
    }
}