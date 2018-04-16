package app.core.h20.events;

import app.model.Frame;
import app.model.Sensor;
import app.sim.SimContext;

public class SensorFrameEvent extends FrameEvent {
    Sensor sensor;

    public SensorFrameEvent (double time, SimContext context, Frame frame, Sensor sensor) {
        super(time, context, frame);
        this.sensor = sensor;
    }

    @Override
    public Sensor getSensor () {
        return sensor;
    }
}
