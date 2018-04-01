package app;

import app.core.scheduler.Scheduler;
import app.model.Sensor;
import app.stats.Collector;

import java.util.List;

public interface SimContext {
    Scheduler getScheduler();

    Collector getCollector();

    double getSim_time();

    List<Sensor> getSensors();

}
