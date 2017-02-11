package net.siekiera.garbageNotifier.service;

import net.siekiera.garbageNotifier.dao.GarbageCollectionDao;
import net.siekiera.garbageNotifier.dao.GarbageTypeDao;
import net.siekiera.garbageNotifier.dao.StreetGroupDao;
import net.siekiera.garbageNotifier.model.*;
import org.apache.commons.collections.IteratorUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by Eric on 05.02.2017.
 */
@Service
public class DataGeneratorService {
    private static final long TYPE_SEGREGATED = 7;
    private static final long TYPE_MIXED = 8;
    private static final long TYPE_ASH = 9;
    private static final long TYPE_LARGE = 10;
    private static final long TYPE_ELECTRO = 11;

    private static final long STREET_GROUP_1 = 5;
    private static final long STREET_GROUP_2 = 6;
    private static final long STREET_GROUP_3 = 7;
    private static final long STREET_GROUP_4 = 8;

    static Logger log = Logger.getLogger(DataGeneratorService.class.getName());

    @Autowired
    GarbageTypeDao garbageTypeDao;
    @Autowired
    StreetGroupDao streetGroupDao;
    @Autowired
    GarbageCollectionDao garbageCollectionDao;

    public GarbageCollection randomGarbageCollection() {
        Iterable<GarbageType> garbageTypes = garbageTypeDao.findAll();
        Iterable<StreetGroup> streetGroups = streetGroupDao.findAll();

        List<GarbageType> garbageTypeList = IteratorUtils.toList(garbageTypes.iterator());
        List<StreetGroup> streetGroupList = IteratorUtils.toList(streetGroups.iterator());

        GarbageCollection garbageCollection = new GarbageCollection();
        garbageCollection.setGarbageType(garbageTypeList.get(randomNumber(garbageTypeList.size())));
        garbageCollection.setStreetGroup(streetGroupList.get(randomNumber(streetGroupList.size())));
        return garbageCollection;
    }

    public void fillCollectionTable() {
        StreetGroup streetGroup = streetGroupDao.findOne(STREET_GROUP_4);
        GarbageType garbageType = garbageTypeDao.findOne(TYPE_ELECTRO);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");


        String[] dates = new String[]{"22.06.2017"};
        for (int i = 0; i < dates.length; i++) {
            try {
                Date date = formatter.parse(dates[i]);
                GarbageCollection garbageCollection = new GarbageCollection(date, garbageType, streetGroup);
                garbageCollectionDao.save(garbageCollection);
            } catch (Exception e) {
                log.error("Złapałem wyjątek parsowania daty: " + e.getMessage());
            }
        }

    }

    public int randomNumber(int max) {
        Random random = new SecureRandom();
        return random.nextInt(max);
    }
}