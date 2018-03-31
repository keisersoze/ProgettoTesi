package app.core.actions.impl.utility;

import app.H2OSim;
import app.SimContext;
import app.core.actions.Action;
import app.core.events.Event;
import org.apache.commons.math3.random.MersenneTwister;

import static org.apache.commons.math3.util.FastMath.log;

public class RescheduleExpRandom implements Action {

    Event e;


    public RescheduleExpRandom( Event e) {
        this.e = e;
    }

    @Override
    public void execute(SimContext context) {

        e.updateTime(-log(H2OSim.MERSENNE_TWISTER.nextDouble()) / H2OSim.LAMDA);
        context.getScheduler().addEvent(e);

    }
}
