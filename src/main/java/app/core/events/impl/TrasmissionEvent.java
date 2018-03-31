package app.core.events.impl;

import app.SimContext;
import app.core.actions.impl.logic.HandleTrasmission;
import app.core.actions.impl.logic.UpdateSNR;
import app.model.Frame;
import app.model.Trasmission;
import app.model.impl.BaseTrasmission;


public class TrasmissionEvent extends BaseEvent{
    public TrasmissionEvent(double time, SimContext context,Frame frame) {
        super(time, context);
        addAction(new HandleTrasmission(frame,this));
        addAction(new UpdateSNR());
    }
}
