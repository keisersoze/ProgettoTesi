package app.sim;

import app.core.scheduler.Scheduler;
import app.model.Frame;
import app.model.Sensor;
import app.stats.Collector;

import java.util.List;

public interface SimContext {
    Scheduler getScheduler();

    Collector getCollector();

    double getSimTime();

    void setSimTime(double x);

    List<Sensor> getSensors();

    List<Frame> getFrames();

    void frameArrived(Frame frame);

    void run();
}
