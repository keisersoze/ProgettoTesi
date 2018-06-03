/*
 * Copyright (c) 2018. Tesi di laurea in Informatica Università Ca' Foscari di Venezia. Creato da Alessio Del Conte e Filippo Maganza
 */

package app.sim.h20serial;

import app.core.Scheduler;
import app.stats.Collector;

public class LambdaSim extends SerialSim {
    public LambdaSim (Collector collector, Scheduler scheduler, String instanceName) {
        super(collector, scheduler, instanceName, 1, 15);
    }

    @Override
    public double getLambda () {
        return super.getSerializedProperty();
    }
}
