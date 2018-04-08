package app.model.h20;

import app.model.Sensor;
import com.jme3.math.Vector3f;

import java.util.List;

public abstract class AbstractSensor  {

    private boolean sink = false;

    protected Vector3f position;

    public AbstractSensor(float x, float y, float z) {
        position = new Vector3f(x, y, z);
    }

    public float getX() {
        return position.x;
    }

    public void setX(float x) {
        position.setX(x);
    }

    public float getY() {
        return position.y;
    }

    public void setY(float y) {
        position.setY(y);
    }

    public float getZ() {
        return position.z;
    }

    public void setZ(float z) {
        position.setZ(z);
    }

    public void setPosition(float x, float y, float z) {
        position.set(x, y, z);
    }

    public boolean isSink() {
        return sink;
    }

    public void setSink(boolean x) {
        sink = x;
    }

    public void setOffsetPosition(float x, float y, float z) {
        position.addLocal(x, y, z);
    }

}
