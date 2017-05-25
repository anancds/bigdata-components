package com.cds.learn;

import static com.cds.learn.GetStarted.wait5Seconds;
import static com.codahale.metrics.MetricRegistry.name;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import com.sun.javafx.font.Metrics;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

/**
 * Created by chendongsheng5 on 2017/5/25.
 */
public class QueueManager {
  private final Queue queue;
  private static final MetricRegistry metrics = new MetricRegistry();

  private int i = 0;
  private final Counter pendingJobs = metrics.counter(name(QueueManager.class, "pending-jobs"));
  private static Histogram histo = metrics.histogram(name(QueueManager.class, "histogram-jobs"));
  private static Timer timer = metrics.timer(name(QueueManager.class, "response"));


  public QueueManager(MetricRegistry metrics, String name) {
    this.queue = new LinkedList();
    metrics.register(name(QueueManager.class, name, "size"),
        new Gauge<Integer>() {
          @Override
          public Integer getValue() {
            return queue.size();
          }
        });
  }

  public void addJob(String job) {
    pendingJobs.inc();
    histo.update(i++);
    queue.offer(job);
  }
  public String takeJob() {
    pendingJobs.dec();
    histo.update(i--);
    return (String) queue.remove();
  }

  public static void main(String[] args) throws InterruptedException {
    ConsoleReporter reporter = ConsoleReporter.forRegistry(metrics)
        .convertRatesTo(TimeUnit.SECONDS)
        .convertDurationsTo(TimeUnit.MILLISECONDS)
        .build();
    reporter.start(1, TimeUnit.SECONDS);
    QueueManager manager = new QueueManager(metrics, "jobs");

    Meter requests = metrics.meter("requests");


    for (int i = 0; i < 1000000; i++) {

      final Timer.Context context = timer.time();
      Thread.sleep(100);
      requests.mark();
      manager.addJob("a");
      context.stop();
    }



    while(true){
      Thread.sleep(1000);
    }
  }


}
