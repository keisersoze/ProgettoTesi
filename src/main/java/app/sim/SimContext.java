package app.sim;

import app.core.scheduler.Scheduler;
import app.factory.CoreFactory;
import app.factory.ModelFactory;
import app.model.Frame;
import app.model.Sensor;
import app.stats.Collector;
import org.apache.commons.math3.random.MersenneTwister;

import java.util.List;

public interface SimContext {
    Scheduler getScheduler ();

    Collector getCollector ();

    double getSimTime ();

    void setSimTime (double x);

    List<? extends Sensor> getSensors ();

    List<? extends Frame> getFrames ();

    void addFrame (Frame frame);

    void frameArrived (Frame frame);

    void run ();

    MersenneTwister getMarsenneTwister ();

    CoreFactory getCoreFactory ();

    ModelFactory getModelFactory ();
}
