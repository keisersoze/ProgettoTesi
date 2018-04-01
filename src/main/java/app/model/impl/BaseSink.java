package app.model.impl;

import app.model.Sensor;
import com.jme3.math.Vector3f;

import java.util.List;

public class BaseSink implements Sensor {

    private Vector3f position;

    public BaseSink(float x, float y, float z) {
        position = new Vector3f(x, y, z);
    }


    @Override
    public float getX() {
        return position.x;
    }

    @Override
    public void setX(float x) {
        position.setX(x);
    }

    @Override
    public float getY() {
        return position.y;
    }

    @Override
    public void setY(float y) {
        position.setY(y);
    }

    @Override
    public float getZ() {
        return position.z;
    }

    @Override
    public void setZ(float z) {
        position.setZ(z);
    }

    @Override
    public void setPosition(float x, float y, float z) {
        position.set(x, y, z);
    }

    @Override
    public Vector3f getPosition() {
        return position;
    }

    @Override
    public List<Sensor> getNeighbors() {
        return null;
    }

    @Override
    public double getEuclideanDistance(Sensor s) {
        return position.distance(s.getPosition());
    }

    @Override
    public boolean isSink() {
        return true;
    }

    @Override
    public void setOffsetPosition(float x, float y, float z) {
        position.addLocal(x, y, z);
    }
}
