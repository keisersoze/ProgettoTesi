package app.core.actions.impl.logic;

import app.SimContext;
import app.core.actions.Action;
import app.core.events.impl.TrasmissionEvent;
import app.model.Sensor;
import org.apache.commons.math3.random.MersenneTwister;

import java.util.List;

public class HandleArrival implements Action {

    private static final MersenneTwister MERSENNE_TWISTER = new MersenneTwister();

    @Override
    public void execute(SimContext context) {
        List<Sensor> sensors= context.getSensors();
        sensors.get(MERSENNE_TWISTER.nextInt(sensors.size()));
        context.getScheduler().addEvent(new TrasmissionEvent(0,context));

    }
}
