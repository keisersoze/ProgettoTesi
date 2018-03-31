package app.core.events.impl;

import app.SimContext;
import app.core.actions.impl.MoveSensors;
import app.core.actions.impl.UpdateSNR;

public class MoveEvent extends BaseEvent {

    public MoveEvent(double time, SimContext context) {
        super(time, context);
        addListener(new MoveSensors());
        addListener(new UpdateSNR());
    }
}
