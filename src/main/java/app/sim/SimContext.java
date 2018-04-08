package app.sim;

import app.core.Scheduler;
import app.core.CoreComponentsFactory;
import app.model.ModelComponentsFactory;
import app.model.Frame;
import app.model.Sensor;
import app.stats.Collector;
import org.apache.commons.math3.random.MersenneTwister;

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

    MersenneTwister getMarsenneTwister();

    CoreComponentsFactory getCoreComponentsFactory();

    ModelComponentsFactory getModelComponentsFactory();
}
