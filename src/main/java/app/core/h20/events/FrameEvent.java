package app.core.h20.events;

import app.model.Frame;
import app.sim.SimContext;

public class FrameEvent extends BaseEvent {
    Frame frame;

    public FrameEvent (double time, SimContext context, Frame frame) {
        super(time, context);
        this.frame = frame;
    }

    @Override
    public Frame getFrame () {
        return frame;
    }
}
