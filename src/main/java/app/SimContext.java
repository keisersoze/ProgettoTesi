package app;

import app.core.scheduler.Scheduler;
import app.model.Frame;
import app.model.Sensor;
import app.stats.Collector;

import java.util.List;

public interface SimContext {
    Scheduler getScheduler();
    Collector getCollector();
    double getSimTime();
    List<Sensor> getSensors();
    void frameArrived();

}
