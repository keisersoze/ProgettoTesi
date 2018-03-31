package app.core.actions.impl.utility;

import app.SimContext;
import app.core.actions.Action;
import app.core.events.Event;
import org.apache.commons.math3.random.MersenneTwister;

import static org.apache.commons.math3.util.FastMath.log;

public class RescheduleExpRandom implements Action {

    private static final MersenneTwister MERSENNE_TWISTER = new MersenneTwister();

    float rate;
    Event e;


    public RescheduleExpRandom(float rate, Event e) {
        this.rate = rate;
        this.e = e;
    }

    @Override
    public void execute(SimContext context) {

        e.updateTime(-log(MERSENNE_TWISTER.nextDouble()) / rate);
        context.getScheduler().addEvent(e);

    }
}
