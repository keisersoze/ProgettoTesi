package evt;

import model.BaseTransmission;

public class EndOfTransmissionEvent extends BaseEvent implements Event {

    private BaseTransmission t;

    public EndOfTransmissionEvent(double remainingTime, BaseTransmission t) {
        super(remainingTime);
        this.t = t;
    }



    public BaseTransmission getTransmission() {
        return t;
    }
}
