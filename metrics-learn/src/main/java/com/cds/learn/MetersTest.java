package com.cds.learn;

import static com.codahale.metrics.MetricRegistry.name;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import java.util.concurrent.TimeUnit;

/**
 * Created by chendongsheng5 on 2017/5/25.
 *
 */
public class MetersTest {

  /**
   * 实例化一个registry，最核心的一个模块，相当于一个应用程序的metrics系统的容器，维护一个Map
   */
  private static final MetricRegistry metrics = new MetricRegistry();
  /**
   * 实例化一个Meter
   */
  private static final Meter requests = metrics.meter(name(MetersTest.class, "request"));
  /**
   * 在控制台上打印输出
   */
  private static ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics).build();

  public static void handleRequest() {
    requests.mark();
  }

  public static void main(String[] args) throws InterruptedException {
    reporter.start(3, TimeUnit.SECONDS);
    while (true) {
      handleRequest();
      Thread.sleep(100);
    }
  }
}
