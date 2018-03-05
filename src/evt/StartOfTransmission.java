package evt;

import model.ent.Sensor;

public class StartOfTransmission extends BaseEvent implements Event {
    private Sensor actor;

    public StartOfTransmission(double remainingTime, Sensor actor) {
        super(remainingTime);
        this.actor=actor;
    }

    @Override
    public void tick() {

    }

    public Sensor getTransmitter() {
        return actor;
    }
}
