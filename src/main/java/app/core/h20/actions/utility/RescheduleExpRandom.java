package app.core.h20.actions.utility;

import app.core.Action;
import app.core.Event;
import app.sim.SimContext;

import static org.apache.commons.math3.util.FastMath.log;

public class RescheduleExpRandom implements Action {

    @Override
    public void execute (Event event) {

        SimContext context = event.getContext();

        event.updateTime(-log(context.getMarsenneTwister().nextDouble()) / context.getLambda());
        context.getScheduler().addEvent(event);

    }

}
