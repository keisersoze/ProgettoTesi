package app.core.h20.events;

import app.sim.SimContext;
import app.model.Frame;


public class TrasmissionEvent extends BaseEvent {
    Frame frame;

    public TrasmissionEvent(double time, SimContext context, Frame frame) {
        super(time, context);
        this.frame = frame;
    }

    @Override
    public Frame getFrame() {
        return frame;
    }
}
