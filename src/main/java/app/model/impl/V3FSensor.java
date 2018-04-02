package app.model.impl;

import app.Canvas;
import app.model.Sensor;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class V3FSensor implements Sensor {
    private Geometry geometry;
    private Canvas canvas;
    private boolean sink;

    public V3FSensor(float x, float y, float z, Canvas canvas) {
        Vector3f position = new Vector3f(x, y, z);
        this.canvas = canvas;
        geometry = canvas.sphereWithTexture(
                30,
                30,
                0.5f,
                position,
                null,
                new ColorRGBA(0f / 255f, 96f / 255f, 255f / 255f, 1f)
        );
    }

    @Override
    public float getX() {
        return geometry.getLocalTranslation().getX();
    }

    @Override
    public void setX(float x) {
        setPosition(x, geometry.getLocalTranslation().y, geometry.getLocalTranslation().z);
    }

    @Override
    public float getY() {
        return geometry.getLocalTranslation().getY();
    }

    @Override
    public void setY(float y) {
        setPosition(geometry.getLocalTranslation().x, y, geometry.getLocalTranslation().z);
    }

    @Override
    public float getZ() {
        return geometry.getLocalTranslation().getZ();
    }

    @Override
    public void setZ(float z) {
        setPosition(geometry.getLocalTranslation().x, geometry.getLocalTranslation().y, z);
    }

    @Override
    public void setPosition(float x, float y, float z) {
        try {
            canvas.enqueue((Callable<Spatial>) () -> {
                geometry.setLocalTranslation(new Vector3f(x, y, z));
                return geometry;
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void setPositionVector3f(Vector3f position) {
        setPosition(position.x, position.y, position.z);
    }

    @Override
    public Vector3f getPosition() {
        return geometry.getLocalTranslation();
    }

    @Override
    public List<Sensor> getNeighbors() {
        return null;
    }

    @Override
    public double getEuclideanDistance(Sensor s) {
        Vector3f vs2 = new Vector3f(s.getX(), s.getY(), s.getZ());
        return geometry.getLocalTranslation().distance(vs2);
    }

    @Override
    public void setOffsetPosition(float x, float y, float z) {
        setPositionVector3f(geometry.getLocalTranslation().add(x, y, z));
    }

    public Geometry getGeometry() {
        return geometry;
    }

    @Override
    public void setSink(boolean x) {
        sink=x;
    }

    @Override
    public boolean isSink() {
        return false;
    }




}
