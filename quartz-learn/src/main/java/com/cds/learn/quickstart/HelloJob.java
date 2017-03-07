package com.cds.learn.quickstart;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * <p></p>
 * author chendongsheng5 2017/3/7 20:28
 * version V1.0
 * modificationHistory =========================逻辑或功能性重大变更记录
 * modify by user: chendongsheng5 2017/3/7 20:28
 * modify by reason:{方法名}:{原因}
 */
public class HelloJob implements Job{


  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {

    System.out.println("hello quartz");
  }
}
