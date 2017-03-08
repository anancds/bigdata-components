package com.cds.learn.quickstart;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.calendar.HolidayCalendar;

/**
 * <p></p>
 * author chendongsheng5 2017/3/8 10:33
 * version V1.0
 * modificationHistory =========================逻辑或功能性重大变更记录
 * modify by user: chendongsheng5 2017/3/8 10:33
 * modify by reason:{方法名}:{原因}
 */
public class CalendarExample {

  //FIXME
  public static void main(String[] args) throws SchedulerException {
    SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();

    Scheduler sched = schedFact.getScheduler();

    sched.start();

    HolidayCalendar cal = new HolidayCalendar();
    cal.addExcludedDate(new Date(System.currentTimeMillis()));
//    cal.addExcludedDate( someOtherDate );

    sched.addCalendar("myHolidays", cal, false, false);

    // define the job and tie it to our HelloJob class
    JobDetail job = newJob(DumbJob.class)
        .withIdentity("myJob", "group1")
        .usingJobData("jobSays", "Hello World!")
        .usingJobData("myFloatValue", 3.141f)
        .build();

    Trigger t = newTrigger()
        .withIdentity("myTrigger", "group1")
//        .forJob("DumbJob")
        .withSchedule(dailyAtHourAndMinute(10, 45)) // execute job daily at 9:30
        .modifiedByCalendar("myHolidays") // but not on holidays
        .build();

    sched.scheduleJob(job, t);
  }

}
