package app.model.h20;

import app.model.Sensor;
import app.model.Transmission;

public class BaseTransmission extends  AbstractTransmission implements Transmission<Sensor>{
    private Sensor sender;
    private Sensor receiver;

    public BaseTransmission(Sensor sender, Sensor receiver) {
        super();
        this.sender = sender;
        this.receiver = receiver;
    }

    public Sensor getSender() {
        return sender;
    }

    public Sensor getReceiver() {
        return receiver;
    }

}
