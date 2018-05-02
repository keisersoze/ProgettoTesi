package app.core.h20Reliable;

import app.core.Action;
import app.core.Event;
import app.model.Sensor;
import app.model.Transmission;
import app.sim.SimContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HandleEndAckReception implements Action {
    private List<Sensor> sensors;

    public HandleEndAckReception() {
        this.sensors = new ArrayList<>();
    }

    @Override
    public void execute(Event e) {

        Transmission ackTransmission = e.getTransmission();
        Sensor receiver = e.getSensor();

        sensors.remove(receiver);

        /*receiver.setReceiving(false);

        if (ackTransmission.isSuccessfull()) {

        }*/
    }

    public List<Sensor> getSensors() {
        return sensors;
    }
}
