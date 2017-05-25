package com.cds.learn;

import static com.codahale.metrics.MetricRegistry.name;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.MetricRegistry;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by chendongsheng5 on 2017/5/25.
 */
public class HistogramsTest {

  /**
   * 实例化一个registry，最核心的一个模块，相当于一个应用程序的metrics系统的容器，维护一个Map
   */
  private static final MetricRegistry metrics = new MetricRegistry();
  /**
   * 实例化一个Histograms
   */
  private static final Histogram randomNums = metrics
      .histogram(name(HistogramsTest.class, "random"));
  /**
   * 在控制台上打印输出
   */
  private static ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics).build();

  public static void handleRequest(double random) {
    randomNums.update((int) (random * 100));
  }

  public static void main(String[] args) throws InterruptedException {
    reporter.start(3, TimeUnit.SECONDS);
    Random rand = new Random();
    while (true) {
      handleRequest(rand.nextDouble());
      Thread.sleep(100);
    }
  }

}
