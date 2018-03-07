package evt;

import model.BaseTransmission;

public class EndOfTransmission extends BaseEvent implements Event {

    private BaseTransmission t;

    public EndOfTransmission(double remainingTime, BaseTransmission t) {
        super(remainingTime);
        this.t = t;
    }



    public BaseTransmission getTransmission() {
        return t;
    }
}
