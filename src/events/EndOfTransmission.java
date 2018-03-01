package events;

import entities.Transmission;

public class EndOfTransmission extends BaseEvent implements Event {

    private Transmission t;

    public EndOfTransmission(double remainingTime, Transmission t) {
        super(remainingTime);
        this.t = t;
    }

    @Override
    public void tick() {

    }

    public Transmission getTransmission() {
        return t;
    }
}
