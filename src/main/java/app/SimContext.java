package app;

import app.core.scheduler.Scheduler;
import app.stats.Collector;

public interface SimContext {
    Scheduler getScheduler();
    Collector getCollector();
    double getSim_time();

}
