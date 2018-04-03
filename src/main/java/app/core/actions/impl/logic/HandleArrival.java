package app.core.actions.impl.logic;

import app.H2OSim;
import app.core.events.Event;
import app.sim.SimContext;
import app.core.actions.Action;
import app.core.events.impl.TrasmissionEvent;
import app.model.Frame;
import app.model.Sensor;
import app.model.impl.BaseFrame;

import java.util.List;

public class HandleArrival implements Action {

    public HandleArrival() {
    }

    @Override
    public void execute(Event event) {

        SimContext context = event.getContext();

        List<Sensor> sensors = context.getSensors();
        Sensor s = sensors.get(H2OSim.MERSENNE_TWISTER.nextInt(sensors.size())); //prendo un sensore a caso

        Frame newFrame = new BaseFrame(H2OSim.MU, s, s);
        context.getScheduler().addEvent(new TrasmissionEvent(0, context, newFrame));
        context.getFrames().add(newFrame);

    }
}
