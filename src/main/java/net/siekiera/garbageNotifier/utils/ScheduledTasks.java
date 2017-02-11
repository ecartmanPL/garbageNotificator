package net.siekiera.garbageNotifier.utils;

import net.siekiera.garbageNotifier.model.GarbageCollection;
import net.siekiera.garbageNotifier.model.UserInfo;
import net.siekiera.garbageNotifier.service.GarbageService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Eric on 08.02.2017.
 */

@Component
public class ScheduledTasks {

    static Logger log = Logger.getLogger(ScheduledTasks.class.getName());
    @Autowired
    GarbageService garbageService;

    @Scheduled(cron = "1 0 0 * * *")
    public void sendDailySmsMessages() {
        log.info("Running cron job!");
        List<GarbageCollection> garbageCollections = garbageService.getNextGarbageCollections();
        log.info("Znalazłem " + garbageCollections.size() + " zbiórek!");
        List<UserInfo> activeUsers = garbageService.getActiveUsers();
        log.info("Znalazłem " + activeUsers.size() + " aktywnych użytkowników.");
        for (UserInfo userInfo : activeUsers) {
            StringBuilder sb = new StringBuilder();
            Date sendingTime = garbageService.smsSendingTime(userInfo.getCzas());
            for (GarbageCollection garbageCollection : garbageCollections) {
                if (garbageCollection.getStreetGroup() == userInfo.getStreet().getStreetGroup()) {
                    if (sb.length() == 0) {
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        sb.append(dateFormat.format(garbageCollection.getDate()) + ": ");
                    }
                    sb.append(garbageCollection.getGarbageType().getName() + ", ");
                }
            }
            if (sb.length() > 0) {
                String smsText = sb.toString();
                //remove last ", "
                smsText = smsText.substring(0, smsText.length() - 2);
                log.info("Wysylam sms o tresci " + smsText + " pod numer " + userInfo.getPhone_number());
                garbageService.sendSmsAtTime(userInfo.getPhone_number(), smsText, sendingTime);
            }
        }
    }
}