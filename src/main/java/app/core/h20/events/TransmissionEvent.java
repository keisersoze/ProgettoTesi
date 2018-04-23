package app.core.h20.events;

import app.model.Transmission;
import app.sim.SimContext;

public class TransmissionEvent extends BaseEvent {

    private Transmission transmission;

    public TransmissionEvent (double time, SimContext context, Transmission transmission) {
        super(time, context);
        this.transmission = transmission;
    }

    @Override
    public Transmission getTransmission () {
        return transmission;
    }
}
