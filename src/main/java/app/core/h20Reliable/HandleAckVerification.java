package app.core.h20Reliable;

import app.core.Action;
import app.core.Event;
import app.factory.ActionTypes;
import app.factory.EventTypes;
import app.model.Frame;
import app.model.Sensor;
import app.sim.SimContext;

import java.util.List;

public class HandleAckVerification implements Action {
    @Override
    public void execute(Event event) {
        SimContext context = event.getContext();
        Frame frame = event.getFrame();
        Sensor sender = event.getSensor();
        int numHop = event.getInt();
        List<Sensor> sensors = ((HandleEndAckReception) context.getCoreFactory().getAction(ActionTypes.HandleEndAckReception)).getSensors();

        if (sensors.contains(sender)){
            Event newEvent= context.getCoreFactory().getEvent(EventTypes.TransmissionEvent,0,context,frame,sender,numHop);
            context.getScheduler().addEvent(newEvent);
            System.out.println(1);
        }


    }
}
