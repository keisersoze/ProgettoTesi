package app.core.h20.actions.logic;

import app.H2OSim;
import app.core.Action;
import app.core.Event;
import app.factory.h20.EventTypes;
import app.model.Transmission;
import app.sim.SimContext;

public class HandleReception implements Action {
    @Override
    public void execute(Event e) {

        SimContext context = e.getContext();
        Transmission transmission = e.getTransmission(); //TODO implementare profondità
        double time = transmission.getFrame().getSize()/H2OSim.SENSOR_BANDWIDTH;
        Event new_e = context.getCoreFactory().getEvent(EventTypes.EndTrasmissionEvent,time,context,transmission);
        context.getScheduler().addEvent(new_e);

    }
}
