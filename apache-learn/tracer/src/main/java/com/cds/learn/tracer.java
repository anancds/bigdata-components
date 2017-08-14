package com.cds.learn;

import org.zalando.tracer.Trace;
import org.zalando.tracer.Tracer;

public class tracer {

  public static void main(String[] args) {
    Tracer tracer = Tracer.create("X-Trace-ID");
//    Tracer tracer = Tracer.builder()
//        .stacked(true)
//        .trace("X-Trace-ID")
    tracer.start();
//        .build();
    Trace trace = tracer.get("X-Trace-ID");
    System.out.println(trace.getName());
//    System.out.println(tracer.snapshot());

  }

}
