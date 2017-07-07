package com.cds.learn.monitor;

public interface ResouceMonitor {

  long getMaxMemory();

  long getUsedMemory();

  int getCores();

  int getUsedCores();
}
