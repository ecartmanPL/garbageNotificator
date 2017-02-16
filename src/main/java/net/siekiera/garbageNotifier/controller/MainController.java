package net.siekiera.garbageNotifier.controller;

import net.siekiera.garbageNotifier.dao.GarbageCollectionDao;
import net.siekiera.garbageNotifier.dao.GarbageTypeDao;
import net.siekiera.garbageNotifier.dao.StreetDao;
import net.siekiera.garbageNotifier.dao.StreetGroupDao;
import net.siekiera.garbageNotifier.model.*;
import net.siekiera.garbageNotifier.service.DataGeneratorService;
import net.siekiera.garbageNotifier.service.GarbageService;
import net.siekiera.garbageNotifier.utils.ScheduledTasks;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Eric on 29.01.2017.
 */
@Controller
public class MainController {
    static Logger log = Logger.getLogger(MainController.class.getName());
    @Autowired
    StreetDao streetDao;
    @Autowired
    GarbageService garbageService;
    @Autowired
    GarbageTypeDao garbageTypeDao;
    @Autowired
    StreetGroupDao streetGroupDao;
    @Autowired
    DataGeneratorService dataGeneratorService;
    @Autowired
    GarbageCollectionDao garbageCollectionDao;
    @Autowired
    ScheduledTasks scheduledTasks;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home(Model model) {
        model.addAttribute("userInfo", new UserInfo());
        model.addAttribute("allStreets", streetDao.findAll());
        return new ModelAndView("addPhoneNumberPage", "model", model);
    }

    @RequestMapping(value = "/sendToken", method = RequestMethod.POST)
    public ModelAndView sendToken(UserInfo userInfo) {
        garbageService.saveOrUpdateUserInfoAndSendSMS(userInfo);
        return new ModelAndView("enterTokenPage", "userInfo", userInfo);
    }

    @RequestMapping(value = "/verifyToken", method = RequestMethod.POST)
    public ModelAndView verifyToken(UserInfo userInfo) {
        Boolean verification = garbageService.verifyTokenAndActivate(userInfo);
        return new ModelAndView("verifyTokenPage", "verification", verification);
    }

    @RequestMapping(value = "/testQuery")
    public ModelAndView testQuery() throws ParseException {
        scheduledTasks.sendDailySmsMessages();
        //garbageService.sendSms("782219369", "To jest test!");
        return null;
    }
}