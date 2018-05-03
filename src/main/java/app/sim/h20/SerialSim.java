package app.sim.h20;

import app.core.Scheduler;
import app.model.Sensor;
import app.stats.Collector;
import app.utils.MyLib;

public class SerialSim extends SimulationInstance {
    private double interval;
    private double property;

    public SerialSim (Collector collector, Scheduler scheduler, String instanceName, double min, double max) {
        super(collector, scheduler, instanceName);
        property = min;
        interval = max;
        setName(instanceName);
    }

    @Override
    public void setPercentageCompleted () {
        super.setPercentageCompleted();
        if (super.getPercentageCompleted() % 20 == 0) { //TODO attenzione
            property += (interval - property) * 0.2;

            //pulisco tutto
            getScheduler().clear();
            getFrames().clear();
            getFramesArrived().clear();
            setSimTime(0);
            for (Sensor s : getSensors()) {
                s.setTransmitting(false);
                s.setReceiving(false);
                s.setWaiting(false);
            }

            //si ricomincia
            for (Sensor sensor : getSensors()) {
                sensor.setNeighbors(MyLib.calculateNeighbors(sensor, this));
            }
            initEvents();
        }
    }

    public double getProperty () {
        return property;
    }
}
