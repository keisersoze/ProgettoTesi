package evt;

import model.Sensor;

public class StartOfTransmissionEvent extends BaseEvent implements Event {
    private Sensor actor;

    public StartOfTransmissionEvent(double remainingTime, Sensor actor) {
        super(remainingTime);
        this.actor=actor;
    }


    public Sensor getTransmitter() {
        return actor;
    }
}
