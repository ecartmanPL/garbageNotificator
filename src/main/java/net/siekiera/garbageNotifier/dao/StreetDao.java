package net.siekiera.garbageNotifier.dao;

import net.siekiera.garbageNotifier.model.Street;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by Eric on 04.02.2017.
 */
@Transactional
public interface StreetDao extends CrudRepository<Street, Long> {
}
