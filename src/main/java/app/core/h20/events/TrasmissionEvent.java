package app.core.events.impl;

import app.model.Frame;
import app.sim.SimContext;


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
