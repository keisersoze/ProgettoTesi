package app.core.actions.impl.logic;

import app.H2OSim;
import app.core.events.Event;
import app.factory.EventTypes;
import app.sim.SimContext;
import app.core.actions.Action;
import app.core.events.impl.TrasmissionEvent;
import app.model.Frame;
import app.model.Sensor;
import app.model.impl.BaseFrame;

import java.util.List;

import static org.apache.commons.math3.util.FastMath.log;

public class HandleArrival implements Action {

    public HandleArrival() {
    }

    @Override
    public void execute(Event event) {

        SimContext context = event.getContext();

        List<Sensor> sensors = context.getSensors();
        Sensor s = sensors.get(context.getMarsenneTwister().nextInt(sensors.size())); //prendo un sensore a caso

        Frame newFrame = new BaseFrame(H2OSim.MU, s, s);
        Event e = context.getCoreComponentsFactory().getEvent(EventTypes.TrasmissionEvent,0, context, newFrame);
        context.getScheduler().addEvent(e);
        context.getFrames().add(newFrame);

    }

}
