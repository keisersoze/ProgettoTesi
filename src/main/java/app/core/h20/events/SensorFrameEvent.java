package app.core.h20.events;

import app.model.Frame;
import app.model.Sensor;
import app.sim.SimContext;

public class SensorFrameEvent extends FrameEvent {
    Sensor sensor;
    int hop;

    public SensorFrameEvent (double time, SimContext context, Frame frame, Sensor sensor, int hop) {
        super(time, context, frame);
        this.sensor = sensor;
        this.hop = hop;
    }

    @Override
    public Sensor getSensor () {
        return sensor;
    }

    @Override
    public int getInt () {
        return hop;
    }
}
