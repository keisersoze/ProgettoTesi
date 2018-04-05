package app.core.events.impl;

import app.sim.SimContext;
import app.core.actions.impl.logic.MoveSensors;
import app.core.actions.impl.logic.UpdateSNR;

public class MoveEvent extends BaseEvent {

    public MoveEvent(double time, SimContext context) {
        super(time, context);
    }
}
