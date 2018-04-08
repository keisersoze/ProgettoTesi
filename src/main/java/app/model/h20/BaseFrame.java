package app.model.h20;

import app.model.Frame;
import app.model.Sensor;

public class BaseFrame extends AbstractFrame implements Frame {



    public BaseFrame(double size, Sensor sender, Sensor currentOwner) {
        super(size,sender,currentOwner);

    }

}
