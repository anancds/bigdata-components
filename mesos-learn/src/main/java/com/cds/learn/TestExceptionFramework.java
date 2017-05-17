package com.cds.learn;

/**
 * <p></p>
 * author chendongsheng5 2017/5/15 14:45
 * version V1.0
 * modificationHistory =========================逻辑或功能性重大变更记录
 * modify by user: chendongsheng5 2017/5/15 14:45
 * modify by reason:{方法名}:{原因}
 */
import java.io.File;

import java.util.List;

import com.google.protobuf.ByteString;

import org.apache.mesos.*;
import org.apache.mesos.Protos.*;


public class TestExceptionFramework {
  static class TestExceptionScheduler implements Scheduler {
    @Override
    public void registered(SchedulerDriver driver,
        FrameworkID frameworkId,
        MasterInfo masterInfo) {
      throw new ArrayIndexOutOfBoundsException();
    }

    @Override
    public void reregistered(SchedulerDriver driver, MasterInfo masterInfo) {}

    @Override
    public void disconnected(SchedulerDriver driver) {}

    @Override
    public void resourceOffers(SchedulerDriver driver,
        List<Offer> offers) {}

    @Override
    public void offerRescinded(SchedulerDriver driver, OfferID offerId) {}

    @Override
    public void statusUpdate(SchedulerDriver driver, TaskStatus status) {}

    @Override
    public void frameworkMessage(SchedulerDriver driver,
        ExecutorID executorId,
        SlaveID slaveId,
        byte[] data) {}

    @Override
    public void slaveLost(SchedulerDriver driver, SlaveID slaveId) {}

    @Override
    public void executorLost(SchedulerDriver driver,
        ExecutorID executorId,
        SlaveID slaveId,
        int status) {}

    public void error(SchedulerDriver driver, String message) {}
  }

  private static void usage() {
    String name = TestExceptionFramework.class.getName();
    System.err.println("Usage: " + name + " master");
  }

  public static void main(String[] args) throws Exception {
    if (args.length != 1) {
      usage();
      System.exit(1);
    }

    FrameworkInfo.Builder frameworkBuilder = FrameworkInfo.newBuilder()
        .setUser("") // Have Mesos fill in the current user.
        .setName("Exception Framework (Java)");

    MesosSchedulerDriver driver = null;
    if (System.getenv("MESOS_AUTHENTICATE_FRAMEWORKS") != null) {
      System.out.println("Enabling authentication for the framework");

      if (System.getenv("DEFAULT_PRINCIPAL") == null) {
        System.err.println("Expecting authentication principal in the environment");
        System.exit(1);
      }

      if (System.getenv("DEFAULT_SECRET") == null) {
        System.err.println("Expecting authentication secret in the environment");
        System.exit(1);
      }

      Credential credential = Credential.newBuilder()
          .setPrincipal(System.getenv("DEFAULT_PRINCIPAL"))
          .setSecret(System.getenv("DEFAULT_SECRET"))
          .build();

      frameworkBuilder.setPrincipal(System.getenv("DEFAULT_PRINCIPAL"));

      driver = new MesosSchedulerDriver(
          new TestExceptionScheduler(),
          frameworkBuilder.build(),
          args[0],
          credential);
    } else {
      frameworkBuilder.setPrincipal("exception-framework-java");

      driver = new MesosSchedulerDriver(
          new TestExceptionScheduler(),
          frameworkBuilder.build(),
          args[0]);
    }

    int status = driver.run() == Status.DRIVER_STOPPED ? 0 : 1;

    // Ensure that the driver process terminates.
    driver.stop();

    System.exit(status);
  }
}
