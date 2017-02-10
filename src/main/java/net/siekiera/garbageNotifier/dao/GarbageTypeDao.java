package net.siekiera.garbageNotifier.dao;

import net.siekiera.garbageNotifier.model.GarbageType;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by Eric on 05.02.2017.
 */
@Transactional
public interface GarbageTypeDao extends CrudRepository<GarbageType, Long> {

}
