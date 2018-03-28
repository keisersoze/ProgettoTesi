package app.evt;

import app.evt.actions.MoveSensors;
import app.evt.actions.UpdateSNR;

public class MoveEvent extends BaseEvent {
    public MoveEvent(double remainingTime) {
        super(remainingTime);
        addListener(new MoveSensors());
        addListener(new UpdateSNR());
    }
}
