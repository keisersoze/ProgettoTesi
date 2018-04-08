package app.sim.impl;

import app.core.CoreComponentsFactory;
import app.core.h20.h20CoreComponentsFactory;
import app.sim.SimContext;
import app.core.Scheduler;
import app.model.Frame;
import app.stats.Collector;
import org.apache.commons.math3.random.MersenneTwister;

public abstract class AbstractSimIstance implements SimContext {
    private final Scheduler scheduler;
    private final Collector collector;
    private final CoreComponentsFactory coreComponentsFactory;
    private double simTime;
    private  final MersenneTwister marsenneTwister = new MersenneTwister();


    //Dati statistici che vengono utilizzati dal collector
    private int nframes; // numero di frame arrivati ai sink

    public AbstractSimIstance(Collector collector, Scheduler scheduler) {

        this.collector = collector;
        this.scheduler = scheduler;
        simTime = 0.0;
        coreComponentsFactory = new h20CoreComponentsFactory();
        nframes = 0;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public double getSimTime() {
        return simTime;
    }

    @Override
    public void setSimTime(double simTime) {
        this.simTime = simTime;
    }

    @Override
    public CoreComponentsFactory getCoreComponentsFactory() {
        return coreComponentsFactory;
    }

    @Override
    public MersenneTwister getMarsenneTwister() {
        return marsenneTwister;
    }

    // metodi per l'update dei dati statistici

    public Collector getCollector() {
        return collector;
    }

    @Override
    public void frameArrived(Frame frame) {
        nframes++;
    }

}
