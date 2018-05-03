/*
 * Copyright (c) 2018. Tesi di laurea in Informatica Università Ca' Foscari di Venezia. Creato da Alessio Del Conte e Filippo Maganza
 */

package app.sim.h20;

import app.H20Sim;
import app.core.Scheduler;
import app.model.Sensor;
import app.stats.Collector;
import app.utils.MyLib;

public class SensorNumberSim extends SerialSim {
    public SensorNumberSim (Collector collector, Scheduler scheduler, String instanceName) {
        super(collector, scheduler, instanceName, 150, 1500);
        getSensors().clear();
        getSensors().addAll(getModelFactory().deploySensors(H20Sim.DEPLOYMENT_TYPE));
    }

    @Override
    public double getSensorsNumber () {
        return super.getProperty();
    }

    @Override
    public void setPercentageCompleted () {
        super.setPercentageCompleted();
        if (super.getPercentageCompleted() % 20 == 0) { //TODO attenzione
            getScheduler().clear();
            getFrames().clear();
            getFramesArrived().clear();
            setSimTime(0);

            for (Sensor s : getSensors()) {
                s.setTransmitting(false);
                s.setReceiving(false);
                s.setWaiting(false);
            }

            getSensors().clear();
            getSensors().addAll(getModelFactory().deploySensors(H20Sim.DEPLOYMENT_TYPE));
            //si ricomincia
            for (Sensor sensor : getSensors()) {
                sensor.setNeighbors(MyLib.calculateNeighbors(sensor, this));
            }
            initEvents();
        }
    }
}
