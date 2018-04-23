package app.sim;

import app.core.Scheduler;
import app.factory.CoreFactory;
import app.factory.ModelFactory;
import app.model.Frame;
import app.model.Sensor;
import app.stats.Collector;
import org.apache.commons.math3.random.MersenneTwister;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface SimContext {
    Scheduler getScheduler ();

    Collector getCollector ();

    double getSimTime ();

    void setSimTime (double x);

    List<Sensor> getSensors ();

    List<Frame> getFrames ();

    Map<Frame, LinkedList<Double>> getFramesArrived ();

    void run ();

    MersenneTwister getMarsenneTwister ();

    CoreFactory getCoreFactory ();

    ModelFactory getModelFactory ();

    double getPercentageCompleted ();

    void setPercentageCompleted ();
}
