package app.core.h20.actions.logic;

import app.H2OSim;
import app.core.Event;
import app.core.h20.EventTypes;
import app.sim.SimContext;
import app.core.Action;
import app.model.Frame;
import app.model.Sensor;

import java.util.List;

import static org.apache.commons.math3.util.FastMath.log;

public class HandleArrival implements Action {

    public HandleArrival() {
    }

    @Override
    public void execute(Event event) {

        SimContext<Sensor,Frame> context = event.getContext();

        List<Sensor> sensors = context.getSensors();
        Sensor s = sensors.get(context.getMarsenneTwister().nextInt(sensors.size())); //prendo un sensore a caso

        Frame newFrame = context.getModelComponentsFactory().getFrame(H2OSim.MU, s, s);
        Event e = context.getCoreComponentsFactory().getEvent(EventTypes.TrasmissionEvent,0, context, newFrame);
        context.getScheduler().addEvent(e);
        context.getFrames().add(newFrame);

    }

}
