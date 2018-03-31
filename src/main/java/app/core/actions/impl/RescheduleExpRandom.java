package app.core.actions.impl;

import app.H2OSim;
import app.SimContext;
import app.core.actions.Action;
import app.core.events.Event;
import org.apache.commons.math3.random.MersenneTwister;

import static org.apache.commons.math3.util.FastMath.log;

public class RescheduleExpRandom implements Action {

    private static final MersenneTwister MERSENNE_TWISTER = new MersenneTwister();

    double rate;
    Event e;


    public RescheduleExpRandom(double rate, Event e) {
        this.rate = rate;
        this.e = e;
    }

    @Override
    public void execute(SimContext context) {


        e.setTime(-log(MERSENNE_TWISTER.nextDouble()) / rate + H2OSim.SAMPLING_INTERVAL);
        context.getScheduler().addEvent(e);

    }
}
