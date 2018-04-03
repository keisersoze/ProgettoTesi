package app.core.actions.impl.utility;

import app.H2OSim;
import app.sim.SimContext;
import app.core.actions.Action;
import app.core.events.Event;

import static org.apache.commons.math3.util.FastMath.log;

public class RescheduleExpRandom implements Action {

    public static final String id="RESCHEDULEEXPRANDOM";

    public RescheduleExpRandom() {

    }

    @Override
    public void execute(Event event) {

        SimContext context = event.getContext();

        event.updateTime(-log(H2OSim.MERSENNE_TWISTER.nextDouble()) / H2OSim.LAMDA);
        context.getScheduler().addEvent(event);

    }
}
