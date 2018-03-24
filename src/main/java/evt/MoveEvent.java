package evt;

import evt.actions.MoveSensors;
import evt.actions.UpdateSNR;

public class MoveEvent extends BaseEvent{
    public MoveEvent(double remainingTime) {
        super(remainingTime);
        addListener(new MoveSensors());
        addListener(new UpdateSNR());
    }
}
