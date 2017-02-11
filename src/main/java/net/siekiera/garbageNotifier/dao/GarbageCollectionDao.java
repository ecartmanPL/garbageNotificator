package net.siekiera.garbageNotifier.dao;

import net.siekiera.garbageNotifier.model.GarbageCollection;
import net.siekiera.garbageNotifier.model.GarbageType;
import net.siekiera.garbageNotifier.model.StreetGroup;
import net.siekiera.garbageNotifier.model.UserInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by Eric on 05.02.2017.
 */
@Transactional
//to zwraca pomieszane idki z datami
//taki sql tez zwraca zle:
//select id, min(date) from sms.garbage_collection where date > sysdate() group by street_group_id, garbage_type_id;
public interface GarbageCollectionDao extends CrudRepository<GarbageCollection, Long> {
    @Query("SELECT min(g.date), g.streetGroup, g.garbageType from GarbageCollection g where g.date > CURRENT_TIMESTAMP group by g.streetGroup, g.garbageType")
    List<Object[]> findNearestCollections();

    @Query("SELECT g from GarbageCollection g where g.date = :date and g.streetGroup = :streetGroup and g.garbageType = :garbageType")
    GarbageCollection findCollectionByDateGroupAndType(@Param("date") Timestamp date, @Param("streetGroup") StreetGroup streetGroup, @Param("garbageType") GarbageType garbageType);

    @Query("SELECT g from GarbageCollection g where g.date = :date")
    List<GarbageCollection> findCollectionsByDate(@Param("date") Date date);
}