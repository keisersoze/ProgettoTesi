package app.core.h20.events;

import app.model.Sensor;
import app.sim.SimContext;

public class SensorEvent extends BaseEvent {
    Sensor sensor;

    public SensorEvent (double time, SimContext context, Sensor sensor) {
        super(time, context);
        this.sensor = sensor;
    }

    @Override
    public Sensor getSensor () {
        return sensor;
    }
}
