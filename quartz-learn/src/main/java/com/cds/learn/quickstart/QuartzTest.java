package com.cds.learn.quickstart;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class QuartzTest {

  public static void main(String[] args) throws SchedulerException {

//    try {
//      // Grab the Scheduler instance from the Factory
//      Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
//
//      // and start it off
//      scheduler.start();
//
//      scheduler.shutdown();
//
//    } catch (SchedulerException se) {
//      se.printStackTrace();
//    }

    SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();

    Scheduler sched = schedFact.getScheduler();

    sched.start();

    // define the job and tie it to our HelloJob class
    JobDetail job = newJob(DumbJob.class)
        .withIdentity("myJob", "group1")
        .usingJobData("jobSays", "Hello World!")
        .usingJobData("myFloatValue", 3.141f)
        .build();

    // Trigger the job to run now, and then every 40 seconds
    Trigger trigger = newTrigger()
        .withIdentity("myTrigger", "group1")
        .startNow()
        .withSchedule(simpleSchedule()
            .withIntervalInSeconds(1)
            .repeatForever())
        .build();

    // Tell quartz to schedule the job using our trigger
    sched.scheduleJob(job, trigger);

//    sched.shutdown();
  }
}
