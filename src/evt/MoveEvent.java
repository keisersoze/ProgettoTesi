package evt;

import evt.listeners.MoveListener;
import evt.listeners.UpdateSNRListener;

public class MoveEvent extends BaseEvent{
    public MoveEvent(double remainingTime) {
        super(remainingTime);
        addListener(new MoveListener());
        addListener(new UpdateSNRListener());
    }
}
