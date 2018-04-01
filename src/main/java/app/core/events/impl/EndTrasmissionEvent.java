package app.core.events.impl;

import app.SimContext;
import app.core.actions.impl.logic.HandleEndTrasmission;
import app.core.actions.impl.logic.UpdateSNR;
import app.model.Frame;

public class EndTrasmissionEvent extends BaseEvent {
    public EndTrasmissionEvent(double time, SimContext context, Frame frame) {
        super(time, context);
        addAction(new HandleEndTrasmission(context, frame));
        addAction(new UpdateSNR());
    }
}
