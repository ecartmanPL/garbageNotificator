package net.siekiera.garbageNotifier.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Eric on 05.02.2017.
 */
@Entity
@Table(name = "garbage_type")
public class GarbageType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String name;
    @OneToMany(mappedBy = "garbageType", cascade = CascadeType.ALL)
    private Set<GarbageCollection> garbageCollections;

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
}
