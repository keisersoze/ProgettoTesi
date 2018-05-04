package app.sim.h20serial;

import app.H20Sim;
import app.core.Scheduler;
import app.model.Sensor;
import app.sim.h20.SimulationInstance;
import app.stats.Collector;
import app.utils.MyLib;

public abstract class SerialSim extends SimulationInstance {
    private double max;
    private double min;

    public SerialSim (Collector collector, Scheduler scheduler, String instanceName, double min, double max) {
        super(collector, scheduler, instanceName);
        this.min = min;
        this.max = max;
        setName(instanceName);
    }

    @Override
    public void setPercentageCompleted () {
        super.setPercentageCompleted();
        if (super.getPercentageCompleted() % 20 == 0) { //TODO attenzione
            min += (max - min) * 0.2;

            //pulisco tutto
            getScheduler().clear();
            getFrames().clear();
            getFramesArrived().clear();
            setSimTime(0);
            getSensors().clear();
            getSensors().addAll(getModelFactory().deploySensors(H20Sim.DEPLOYMENT_TYPE));

            //inizializzazione
            for (Sensor sensor : getSensors()) {
                sensor.setNeighbors(MyLib.calculateNeighbors(sensor, this));
            }
            initEvents();

        }
    }

    public double getSerializedProperty(){
        return min;
    }
}