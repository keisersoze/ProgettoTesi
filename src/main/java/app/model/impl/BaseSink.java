package app.model.impl;

import app.model.Sensor;

import java.util.List;

public class BaseSink implements Sensor {
    private static final int N_DIMENSIONS =3;

    private float[] posVector;

    public BaseSink(float x, float y, float z) {
        posVector = new float[N_DIMENSIONS];
        this.posVector[1] = x;
        this.posVector[2] = y;
        this.posVector[3] = z;
    }


    @Override
    public float getX() {
        return posVector[1];
    }

    @Override
    public float getY() {
        return posVector[2];
    }

    @Override
    public float getZ() {
        return posVector[3];
    }

    @Override
    public List<Sensor> getNeighbors() {
        return null;
    }

    @Override
    public double getEuclideanDistance(Sensor s) {
        return 0;
    }

    @Override
    public boolean isSink() {
        return true;
    }
}
