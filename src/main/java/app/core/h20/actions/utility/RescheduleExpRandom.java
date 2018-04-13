package app.core.h20.actions.utility;

import app.H2OSim;
import app.core.Action;
import app.core.Event;
import app.sim.SimContext;

import static org.apache.commons.math3.util.FastMath.log;

public class RescheduleExpRandom implements Action {

    public RescheduleExpRandom () {

    }

    @Override
    public void execute (Event event) {

        SimContext context = event.getContext();

        event.updateTime(-log(context.getMarsenneTwister().nextDouble()) / H2OSim.LAMDA);
        context.getScheduler().addEvent(event);

    }

}
