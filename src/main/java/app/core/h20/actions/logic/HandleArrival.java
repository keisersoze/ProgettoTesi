package app.core.h20.actions.logic;

import app.H2OSim;
import app.core.Action;
import app.core.Event;
import app.factory.h20.EventTypes;
import app.model.Frame;
import app.model.Sensor;
import app.sim.SimContext;

import java.util.List;

public class HandleArrival implements Action {

    public HandleArrival() {
    }

    @Override
    public void execute(Event event) {

        SimContext context = event.getContext();

        List<Sensor> sensors = context.getSensors();
        Sensor s = sensors.get(context.getMarsenneTwister().nextInt(sensors.size())); //prendo un sensore a caso

        Frame newFrame = context.getModelFactory().getFrame(H2OSim.MU, s, s);
        Event e = context.getCoreFactory().getEvent(EventTypes.TrasmissionEvent, 0, context, newFrame);
        context.getScheduler().addEvent(e);
        context.addFrame(newFrame);

    }

}
