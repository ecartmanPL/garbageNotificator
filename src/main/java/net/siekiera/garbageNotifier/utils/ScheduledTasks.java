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

    @Scheduled(cron="0 0 23 * * *")
    public void sendDailySmsMessages() {
        log.info("Running cron job!");
        //najblizsze zbiorki dla wszystkich grup ulic i rodzajow smieci
        //List<GarbageCollection> garbageCollections = garbageService.getNextGarbageCollections();
        List<GarbageCollection> garbageCollections = garbageService.getAllCollections();
        //aktywni uzytkownicy (tacy ktorzy chca dostawac smsy)
        List<UserInfo> activeUsers = garbageService.getActiveUsers();
        for (UserInfo userInfo : activeUsers) {
            Date sendingTime = sendingTime(userInfo.getCzas());
            StringBuilder sb = new StringBuilder();
            for (GarbageCollection garbageCollection : garbageCollections) {
                //tworzymy datę do porównania (odejmujemy od daty zbiorki 24h)
                Calendar calendarCollection = Calendar.getInstance();
                calendarCollection.setTime(garbageCollection.getDate());
                calendarCollection.add(Calendar.DAY_OF_MONTH, -1);
                Date collectionTime = calendarCollection.getTime();
                if (collectionTime.before(sendingTime)) {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    sb.append(dateFormat.format(garbageCollection.getDate()) + " ");
                    sb.append(garbageCollection.getGarbageType().getName() + " ");
                }
            }
            if (sb.length() > 0) {
                garbageService.sendSmsAfter(userInfo.getPhone_number(), sb.toString(), sendingTime);
            }
        }
    }

    private Date sendingTime(Integer smsReceiveHour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.HOUR_OF_DAY, smsReceiveHour);
        return calendar.getTime();
    }
}
