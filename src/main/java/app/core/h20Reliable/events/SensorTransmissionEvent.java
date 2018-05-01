package app.core.h20Reliable.events;

import app.core.h20.events.TransmissionEvent;
import app.model.Sensor;
import app.model.Transmission;
import app.sim.SimContext;

public class SensorTransmissionEvent extends TransmissionEvent {
    Sensor sensor;

    public SensorTransmissionEvent(double time, SimContext context, Transmission transmission, Sensor sensor) {
        super(time, context, transmission);
        this.sensor= sensor;
    }

    @Override
    public Sensor getSensor() {
        return sensor;
    }
}
