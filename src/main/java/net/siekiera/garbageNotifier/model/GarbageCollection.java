package net.siekiera.garbageNotifier.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Eric on 05.02.2017.
 */
@Entity
@Table(name = "garbage_collection")
public class GarbageCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private Date date;
    @ManyToOne
    @JoinColumn(name = "garbage_type_id")
    private GarbageType garbageType;
    @ManyToOne
    @JoinColumn(name = "street_group_id")
    private StreetGroup streetGroup;

    public GarbageCollection(Date date, GarbageType garbageType, StreetGroup streetGroup) {
        this.date = date;
        this.garbageType = garbageType;
        this.streetGroup = streetGroup;
    }

    public GarbageCollection() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public StreetGroup getStreetGroup() {
        return streetGroup;
    }

    public void setStreetGroup(StreetGroup streetGroup) {
        this.streetGroup = streetGroup;
    }

    public GarbageType getGarbageType() {
        return garbageType;
    }

    public void setGarbageType(GarbageType garbageType) {
        this.garbageType = garbageType;
    }
}