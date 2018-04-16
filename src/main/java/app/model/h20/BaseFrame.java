package app.model.h20;

import app.model.Frame;
import app.model.Sensor;

public class BaseFrame extends AbstractFrame implements Frame {

    private static int maxId = 0;
    private int serialId;

    public BaseFrame (double size, Sensor s) {
        super(size, s);
        serialId = maxId;
        maxId++;

    }

    public int getSerial () {
        return serialId;
    }
}
