package events;

import events.Event;

public class TrasmissionEvent extends AbstractEvent {

    public TrasmissionEvent(double remainingTime) {
        super(remainingTime);
    }

    @Override
    public void tick() {

    }

    @Override
    public int compareTo(Event o) {
        return Double.compare(this.remainingTime,o.getRemainingTime());
    }
}
