package app.sim.h20;

import app.H20Sim;
import app.core.Scheduler;
import app.factory.ModelFactory;
import app.factory.h20.MyModelFactory;
import app.model.Frame;
import app.model.Sensor;
import app.sim.SimContext;
import app.stats.Collector;
import org.apache.commons.math3.random.MersenneTwister;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractSimIstance implements SimContext {
    private final Scheduler scheduler;
    private final Collector collector;
    private final Map<Frame, LinkedList<Double>> framesArrived;
    private final List<Sensor> sensors;
    private final List<Frame> frames;
    private final ModelFactory modelFactory;
    private final MersenneTwister marsenneTwister = new MersenneTwister();
    private double nSamples;
    private double simTime;
    //Dati statistici che vengono utilizzati dal collector

    AbstractSimIstance (Collector collector, Scheduler scheduler) {
        frames = new ArrayList<>();
        this.collector = collector;
        this.scheduler = scheduler;
        simTime = 0.0;
        framesArrived = new ConcurrentHashMap<>();
        modelFactory = new MyModelFactory();
        sensors = modelFactory.deploySensors(H20Sim.DEPLOYMENT_TYPE);
        nSamples = 0;
    }

    public Scheduler getScheduler () {
        return scheduler;
    }

    public double getSimTime () {
        return simTime;
    }

    @Override
    public void setSimTime (double simTime) {
        this.simTime = simTime;
    }


    @Override
    public MersenneTwister getMarsenneTwister () {
        return marsenneTwister;
    }

    // metodi per l'update dei dati statistici

    public Collector getCollector () {
        return collector;
    }

    @Override
    public Map<Frame, LinkedList<Double>> getFramesArrived () {
        return framesArrived;
    }

    @Override
    public List<Sensor> getSensors () {
        return sensors;
    }

    @Override
    public List<Frame> getFrames () {
        return frames;
    }

    @Override
    public ModelFactory getModelFactory () {
        return modelFactory;
    }

    @Override
    public double getPercentageCompleted () {
        return nSamples / H20Sim.N_SAMPLES * 100;
    }

    @Override
    public void setPercentageCompleted () {
        nSamples++;
    }
}
