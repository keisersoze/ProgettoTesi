package app.model.impl;

import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;

import java.util.LinkedList;
import java.util.List;

public class BaseFrame extends AbstractFrame implements Frame {



    public BaseFrame(double size, Sensor sender, Sensor currentOwner) {
        super(size,sender,currentOwner);

    }

}
