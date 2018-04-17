package app.core.h20.actions.logic;

import app.H2OSim;
import app.core.Action;
import app.core.Event;
import app.factory.h20.EventTypes;
import app.model.Sensor;
import app.model.Transmission;
import app.sim.SimContext;

public class HandleReception implements Action {
    @Override
    public void execute (Event event) {

        SimContext context = event.getContext();
        Transmission transmission = event.getTransmission();
        Sensor receiver = transmission.getReceiver();

        if(!receiver.isTransmitting() && !receiver.isReceiving()) {
            receiver.setReceiving(true);
            double time = transmission.getFrame().getSize() / H2OSim.SENSOR_BANDWIDTH;
            Event e = context.getCoreFactory().getEvent(EventTypes.EndReceptionEvent, time, context, transmission);
            context.getScheduler().addEvent(e);
        }

    }
}
