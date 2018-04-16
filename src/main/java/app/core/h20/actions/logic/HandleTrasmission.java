package app.core.h20.actions.logic;

import app.H2OSim;
import app.core.Action;
import app.core.Event;
import app.factory.h20.EventTypes;
import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.sim.SimContext;

public class HandleTrasmission implements Action {


    public HandleTrasmission() {
    }

    @Override
    public void execute(Event event) {

        SimContext context = event.getContext();
        Frame frame = event.getFrame();
        Sensor sender = event.getSensor();
        for (Sensor receiver : sender.getNeighbors()) {

            Transmission transmission = context.getModelFactory().getTransmission(sender, receiver, frame);
            double time = sender.getEuclideanDistance(receiver) / H2OSim.SOUND_SPEED;
            Event e = context.getCoreFactory().getEvent(EventTypes.ReceivingTransmissionEvent, time, context, transmission);
            context.getScheduler().addEvent(e);
        }

    }

}
