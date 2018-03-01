package events;

import entities.SensorListener;

public class StartOfTransmission extends BaseEvent implements Event {
    private SensorListener actor;

    public StartOfTransmission(double remainingTime, SensorListener actor) {
        super(remainingTime);
        this.actor=actor;
    }

    @Override
    public void tick() {

    }

    public SensorListener getTransmitter() {
        return actor;
    }
}
