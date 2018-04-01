package app.core.actions.impl.logic;

import app.H2OSim;
import app.SimContext;
import app.core.actions.Action;
import app.core.events.impl.TrasmissionEvent;
import app.model.Sensor;
import app.model.impl.BaseFrame;

import java.util.List;

public class HandleArrival implements Action {

    @Override
    public void execute(SimContext context) {

        List<Sensor> sensors = context.getSensors();
        Sensor s = sensors.get(H2OSim.MERSENNE_TWISTER.nextInt(sensors.size())); //prendo un sensore a caso

        context.getScheduler().addEvent(new TrasmissionEvent(0, context, new BaseFrame(H2OSim.MU, s, s)));

    }
}
