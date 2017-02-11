package net.siekiera.garbageNotifier.service;

import net.siekiera.garbageNotifier.dao.GarbageCollectionDao;
import net.siekiera.garbageNotifier.dao.StreetGroupDao;
import net.siekiera.garbageNotifier.model.*;
import net.siekiera.garbageNotifier.dao.SmsOutboxDao;
import net.siekiera.garbageNotifier.dao.UserInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.*;

import org.apache.log4j.Logger;

/**
 * Created by Eric on 05.02.2017.
 */
@Service
public class GarbageService {
    static Logger log = Logger.getLogger(GarbageService.class.getName());
    @Autowired
    SmsOutboxDao smsOutboxDao;
    @Autowired
    UserInfoDao userInfoDao;
    @Autowired
    StreetGroupDao streetGroupDao;
    @Autowired
    GarbageCollectionDao garbageCollectionDao;
    @Autowired
    GarbageService garbageService;

    char[] CHARSET_AZ_09 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    char[] SIMPLE = "ABC123".toCharArray();

    public String generateToken(int length) {
        Random random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            //sb.append(CHARSET_AZ_09[random.nextInt(CHARSET_AZ_09.length)]);
            sb.append(SIMPLE[random.nextInt(SIMPLE.length)]);
        }
        return sb.toString();
    }

    public void sendSms(String number, String text) {
        SmsOutbox sms = new SmsOutbox();
        sms.setDestinationNumber(number);
        sms.setTextDecoded(text);
        smsOutboxDao.save(sms);
    }

    public void sendSmsAtTime(String number, String text, Date timestamp) {
        SmsOutbox sms = new SmsOutbox();
        sms.setDestinationNumber(number);
        sms.setTextDecoded(text);
        sms.setSendingDateTime(timestamp);
        smsOutboxDao.save(sms);
    }

    //generuje i przypisuje token do UserInfo. W razie potrzeby aktualizuje ulicę.
    public void saveOrUpdateUserInfoAndSendSMS(UserInfo userInfo) {
        List<UserInfo> userInfoList = userInfoDao.findUserInfoByPhoneNumberList(userInfo.getPhone_number());
        if (userInfoList.size() == 0) {
            String token = generateToken(8);
            userInfo.setToken(token);
            userInfoDao.save(userInfo);
            sendSms(userInfo.getPhone_number(), "Twój token: " + token);
        } else if (userInfoList.size() == 1) {
            //sprawdzic czy aktywny!
            UserInfo userInfoFromDB = userInfoList.get(0);
            String token = generateToken(8);
            userInfoFromDB.setToken(token);
            userInfoFromDB.setStreet(userInfo.getStreet());
            userInfoDao.save(userInfoFromDB);
            sendSms(userInfoFromDB.getPhone_number(), "Twój token: " + token);
        } else {
            log.error("W bazie znajdują się co najmniej 2 takie same numery!");
        }
    }

    public Boolean verifyTokenAndActivate(UserInfo userInfo) {
        UserInfo userInfoFromDB = userInfoDao.findUserInfoByPhoneNumber(userInfo.getPhone_number());
        if (userInfoFromDB.getToken() != null && userInfoFromDB.getToken().length() > 0) {
            if (userInfo.getTokenCheck().equals(userInfoFromDB.getToken())) {
                activateUserInfo(userInfoFromDB);
                return true;
            }
        }
        return false;
    }

    private void activateUserInfo(UserInfo userInfoFromDB) {
        userInfoFromDB.setActive(true);
        userInfoDao.save(userInfoFromDB);
    }

    /**
     * Returns next garbage collections.
     *
     * @return
     */
    public List getNextGarbageCollections() {
        Date nextMidnight = nextMidnight();
        List<GarbageCollection> garbageCollections = garbageCollectionDao.findCollectionsByDate(nextMidnight);
        return garbageCollections;
    }

    public List getActiveUsers() {
        return userInfoDao.findActiveUsers();
    }


    public List<GarbageCollection> getAllCollections() {
        List<GarbageCollection> garbageCollections = new ArrayList<GarbageCollection>();
        for (GarbageCollection singleGarbageCollection : garbageCollectionDao.findAll()) {
            garbageCollections.add(singleGarbageCollection);
        }
        return garbageCollections;
    }

    public Date nextMidnight() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    public Date smsSendingTime(Integer czas) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nextMidnight());
        calendar.add(Calendar.HOUR_OF_DAY, czas);
        return calendar.getTime();

    }
}
