/*
 * Copyright (c) 2018. Tesi di laurea in Informatica Università Ca' Foscari di Venezia. Creato da Alessio Del Conte e Filippo Maganza
 */

package app.sim.h20;

import app.core.Scheduler;
import app.stats.Collector;

public class LambdaSim extends SerialSim {
    public LambdaSim (Collector collector, Scheduler scheduler, String instanceName) {
        super(collector, scheduler, instanceName, 0.01, 5);
    }

    @Override
    public double getLambda () {
        return super.getProperty();
    }
}
