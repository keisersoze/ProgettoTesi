package app.core.h20.actions.logic;

import app.H20Sim;
import app.core.Action;
import app.core.Event;
import app.factory.EventTypes;
import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.sim.SimContext;
import app.utils.MyLib;


public class HandleEndReception implements Action {

    @Override
    public void execute (Event event) {

        SimContext context = event.getContext();
        Transmission transmission = event.getTransmission();
        Frame frame = transmission.getFrame();
        Sensor receiver = transmission.getReceiver();
        int numHop = transmission.getHop() + 1;

        receiver.setReceiving(false);
        transmission.getFrame().getTransmissions().remove(transmission);

        if (transmission.isSuccessfull()) {
            if (!transmission.getReceiver().isSink()) {
                if (MyLib.deterministicProtocol(transmission, context)) {
                    double time = H20Sim.SLOW_RETRANSMITION == true ? MyLib.random(0.2f, 0.4f) : 0;
                    Event e = context.getCoreFactory().getEvent(EventTypes.TransmissionEvent, time, context, frame, receiver, numHop);
                    context.getScheduler().addEvent(e);
                }
            } else {
                context.getFramesArrived().get(frame).addLast(context.getSimTime() - frame.getArrivalTime());
            }
        }
    }


}
