package app.core.h20Reliable.ack;

import app.core.Action;
import app.core.Event;
import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.sim.SimContext;

import java.util.HashMap;
import java.util.Map;

public class HandleEndAckReception implements Action {
    private Map<Sensor,Event> sensorEventMap;

    public HandleEndAckReception() {
        this.sensorEventMap = new HashMap<>();
    }

    @Override
    public void execute(Event e) {
        SimContext context = e.getContext();
        Transmission ackTransmission = e.getTransmission();
        Sensor receiver = e.getSensor();

        receiver.setReceiving(false);

        if (ackTransmission.isSuccessfull()) {
            if (sensorEventMap.containsKey(receiver))
                context.getScheduler().removeEvent(sensorEventMap.get(receiver));
        }
    }

    public void add (Sensor s, Event e){
        sensorEventMap.put(s,e);
    }
}
