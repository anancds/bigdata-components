package com.cds.learn;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class HelloWorld {

  private static void testScheduler() throws InterruptedException {
    Flowable.fromCallable(() -> {
      Thread.sleep(1000); //  imitate expensive computation
      return "Done";
    })
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.single())
        .subscribe(System.out::println, Throwable::printStackTrace);

    Thread.sleep(2000); // <--- wait for the flow to finish
  }

  public static void main(String[] args) throws InterruptedException {
    Flowable.just("Hello world").subscribe(System.out::println);
    testScheduler();
  }
}
