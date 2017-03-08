package com.cds.learn.quickstart;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

/**
 * <p></p>
 * author chendongsheng5 2017/3/8 10:19
 * version V1.0
 * modificationHistory =========================逻辑或功能性重大变更记录
 * modify by user: chendongsheng5 2017/3/8 10:19
 * modify by reason:{方法名}:{原因}
 */
public class DumbJob implements Job {

  public DumbJob() {
  }

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {

    JobKey key = context.getJobDetail().getKey();

    JobDataMap dataMap = context.getJobDetail().getJobDataMap();

    String jobSays = dataMap.getString("jobSays");
    float myFloatValue = dataMap.getFloat("myFloatValue");

    System.err.println(
        "Instance " + key + " of DumbJob says: " + jobSays + ", and val is: " + myFloatValue);
  }
}
