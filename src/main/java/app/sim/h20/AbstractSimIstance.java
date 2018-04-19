package app.sim.h20;

import app.core.Scheduler;
import app.model.Frame;
import app.sim.SimContext;
import app.stats.Collector;
import org.apache.commons.math3.random.MersenneTwister;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public abstract class AbstractSimIstance implements SimContext {
    private final Scheduler scheduler;
    private final Collector collector;
    private final Map<Frame, LinkedList<Double>> framesArrived;

    private final MersenneTwister marsenneTwister = new MersenneTwister();
    private double simTime;
    //Dati statistici che vengono utilizzati dal collector
    private int nframes; // numero di frame arrivati ai sink

    AbstractSimIstance (Collector collector, Scheduler scheduler) {
        this.collector = collector;
        this.scheduler = scheduler;
        simTime = 0.0;
        nframes = 0;
        framesArrived = new HashMap<>();

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
    public Map<Frame, LinkedList<Double>> getFramesArrived() {
        return framesArrived;
    }

}
