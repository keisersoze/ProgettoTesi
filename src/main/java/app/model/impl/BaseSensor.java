package app.model.impl;

import app.model.Sensor;
import org.apache.commons.math3.ml.distance.EuclideanDistance;

import java.util.List;

public class BaseSensor implements Sensor {

    private static final EuclideanDistance EUCLIDEAN_DISTANCE = new EuclideanDistance();
    private static final int N_DIMENSIONS = 3;

    private float[] posVector;

    public BaseSensor(float x, float y, float z) {
        posVector = new float[N_DIMENSIONS];
        this.posVector[0] = x;
        this.posVector[1] = y;
        this.posVector[2] = z;
    }


    @Override
    public float getX() {
        return posVector[0];
    }

    @Override
    public void setX(float x) {
        posVector[0] = x;
    }

    @Override
    public float getY() {
        return posVector[1];
    }

    @Override
    public void setY(float y) {
        posVector[0] = y;
    }

    @Override
    public float getZ() {
        return posVector[2];
    }

    @Override
    public void setZ(float z) {
        posVector[0] = z;
    }

    @Override
    public void setPosition(float x, float y, float z) {
        setX(x);
        setY(y);
        setZ(z);
    }

    @Override
    public List<Sensor> getNeighbors() {
        return null;
    }

    @Override
    public double getEuclideanDistance(Sensor s) {

        double[] vs2 = new double[N_DIMENSIONS];
        vs2[0] = s.getX();
        vs2[1] = s.getY();
        vs2[2] = s.getZ();

        double[] vs1 = new double[N_DIMENSIONS];
        vs1[0] = (double) posVector[0];
        vs1[1] = (double) posVector[1];
        vs1[2] = (double) posVector[2];

        return EUCLIDEAN_DISTANCE.compute(vs1, vs2);
    }
}
