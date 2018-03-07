package evt;

import model.Sensor;

public class StartOfTransmission extends BaseEvent implements Event {
    private Sensor actor;

    public StartOfTransmission(double remainingTime, Sensor actor) {
        super(remainingTime);
        this.actor=actor;
    }


    public Sensor getTransmitter() {
        return actor;
    }
}
