/*
 * Copyright (c) 2018. Tesi di laurea in Informatica Università Ca' Foscari di Venezia. Creato da Alessio Del Conte e Filippo Maganza
 */

package app.sim.h20serial;

import app.core.Scheduler;
import app.stats.Collector;

public class ThresholdSim extends SerialSim {
    public ThresholdSim (Collector collector, Scheduler scheduler, String instanceName) {
        super(collector, scheduler, instanceName, 500, 1300);
    }

    @Override
    public double getThreeshold () {
        return super.getSerializedProperty();
    }
}
