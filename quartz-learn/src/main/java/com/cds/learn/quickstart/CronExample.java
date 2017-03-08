package com.cds.learn.quickstart;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;

/**
 * <p></p>
 * author chendongsheng5 2017/3/8 11:02
 * version V1.0
 * modificationHistory =========================逻辑或功能性重大变更记录
 * modify by user: chendongsheng5 2017/3/8 11:02
 * modify by reason:{方法名}:{原因}
 */
public class CronExample {

  public static void main(String[] args) throws SchedulerException {
    SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();

    Scheduler sched = schedFact.getScheduler();

    sched.start();

    // define the job and tie it to our HelloJob class
    JobDetail job = newJob(DumbJob.class)
        .withIdentity("myJob", "group1")
        .usingJobData("jobSays", "Hello World!")
        .usingJobData("myFloatValue", 3.141f)
        .build();

    String cronExpression = "30/5 * * * * ?"; // 每分钟的30s起，每5s触发任务

    Trigger trigger = newTrigger()
        .withSchedule(cronSchedule("0 0/1 * * * ?"))
        .forJob("myJob", "group1")
        .build();

    sched.scheduleJob(job, trigger);
  }

}
