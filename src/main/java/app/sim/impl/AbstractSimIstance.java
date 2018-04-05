package app.sim.impl;

import app.factory.CoreComponentsFactory;
import app.factory.impl.MyCoreComponentsFactory;
import app.sim.SimContext;
import app.core.scheduler.Scheduler;
import app.model.Frame;
import app.model.Sensor;
import app.stats.Collector;
import org.apache.commons.math3.random.MersenneTwister;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSimIstance implements SimContext {
    private final Scheduler scheduler;
    private final Collector collector;
    private final List<Sensor> sensors;
    private final List<Frame> frames;
    private final CoreComponentsFactory coreComponentsFactory;
    private double simTime;
    private  final MersenneTwister marsenneTwister = new MersenneTwister();


    //Dati statistici che vengono utilizzati dal collector
    private int nframes; // numero di frame arrivati ai sink

    public AbstractSimIstance(Collector collector, Scheduler scheduler) {

        this.collector = collector;
        this.scheduler = scheduler;
        simTime = 0.0;
        sensors = new ArrayList<>();
        frames = new ArrayList<>();
        coreComponentsFactory = new MyCoreComponentsFactory();
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
    public List<Sensor> getSensors() {
        return sensors;
    }

    @Override
    public List<Frame> getFrames() {
        return frames;
    }

    public void removeFrame(Frame f){
        frames.remove(f);
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
