package app.model.h20;

import app.model.Frame;
import app.model.Sensor;

public class BaseTransmission extends AbstractTransmission {

    public BaseTransmission (Sensor sender, Sensor receiver, Frame frame, int hop) {
        super(sender, receiver, frame, hop);
    }
}
