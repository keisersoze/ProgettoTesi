package app.core.h20Reliable;

import app.core.Action;
import app.core.Event;
import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.sim.SimContext;

public class HandleEndAckReception implements Action {
    @Override
    public void execute(Event e) {
        SimContext context = e.getContext();
        Transmission transmission = e.getTransmission();
        Frame frame = transmission.getFrame();
        Sensor sender = transmission.getReceiver();
        Sensor receiver = transmission.getSender();
        int numHop = transmission.getHop() + 1;

        receiver.setReceiving(false);
        transmission.getFrame().getTransmissions().remove(transmission);

        if (transmission.isSuccessfull()) {

        }

    }
}
