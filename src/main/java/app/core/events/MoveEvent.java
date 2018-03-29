package app.core.events;

import app.SimContext;
import app.core.actions.MoveSensors;
import app.core.actions.UpdateSNR;

public class MoveEvent extends BaseEvent {

    public MoveEvent(double time, SimContext context) {
        super(time, context);
        addListener(new MoveSensors());
        addListener(new UpdateSNR());
    }
}
