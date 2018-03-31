package app.model.impl;

import app.model.Sensor;
import org.apache.commons.math3.ml.distance.EuclideanDistance;

import java.util.List;

public class BaseSensor implements Sensor {

    private static final EuclideanDistance EUCLIDEAN_DISTANCE = new EuclideanDistance();
    private static final int N_DIMENSIONS =3;

    private double [] posVector;

    public BaseSensor(double x, double y, double z) {
        posVector = new double[N_DIMENSIONS];
        this.posVector[1] = x;
        this.posVector[2] = y;
        this.posVector[3] = z;
    }


    @Override
    public double[] getPosVector() {
        return posVector;
    }

    @Override
    public double getX() {
        return posVector[1];
    }

    @Override
    public double getY() {
        return posVector[2];
    }

    @Override
    public double getZ() {
        return posVector[3];
    }

    @Override
    public List<Sensor> getNeighbors() {
        return null;
    }

    @Override
    public double getEuclideanDistance(Sensor s) {
        return EUCLIDEAN_DISTANCE.compute(this.posVector,s.getPosVector());
    }
}
