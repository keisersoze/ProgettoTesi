package app.core.events.impl;

import app.sim.SimContext;
import app.core.actions.impl.logic.HandleEndTrasmission;
import app.core.actions.impl.logic.UpdateSNR;
import app.model.Frame;

public class EndTrasmissionEvent extends BaseEvent {
    Frame frame;
    public EndTrasmissionEvent(double time, SimContext context, Frame frame) {
        super(time, context);
        this.frame = frame;
        addAction(new HandleEndTrasmission());
        addAction(new UpdateSNR());
    }

    @Override
    public Frame getFrame() {
        return frame;
    }
}
