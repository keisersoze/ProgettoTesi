package evt;

import ent.BaseTransmission;

public class EndOfTransmission extends BaseEvent implements Event {

    private BaseTransmission t;

    public EndOfTransmission(double remainingTime, BaseTransmission t) {
        super(remainingTime);
        this.t = t;
    }

    @Override
    public void tick() {

    }

    public BaseTransmission getTransmission() {
        return t;
    }
}
