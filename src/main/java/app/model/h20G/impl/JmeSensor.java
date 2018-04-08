package app.model.h20G.impl;

import app.sim.h20G.Canvas;
import app.model.h20.AbstractSensor;
import app.model.h20G.GraphicSensor;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class JmeSensor extends AbstractSensor implements GraphicSensor{


    private Geometry geometry;
    private Canvas canvas;

    public JmeSensor(float x, float y, float z) {
        super(x, y, z);

    }

    @Override
    public void setCanvas(Canvas canvas) {
        Vector3f position = new Vector3f(getX(), getY(), getZ());
        this.canvas = canvas;
        try {
            geometry = canvas.enqueue(() -> canvas.sphereWithTexture(
                    30,
                    30,
                    0.5f,
                    position,
                    null,
                    new ColorRGBA(0f / 255f, 0f / 255f, 255f / 255f, 1f))
            ).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Geometry getGeometry() {
        return geometry;
    }

    @Override
    public void updatePosition() {
        try {
            canvas.enqueue((Callable<Spatial>) () -> {
                geometry.setLocalTranslation(new Vector3f(getPosition()));
                return geometry;
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Vector3f getPosition() {
        return position;
    }

    @Override
    public List<GraphicSensor> getNeighbors() throws ClassCastException{
        return null;
    }

    @Override
    public double getEuclideanDistance(GraphicSensor graphicSensor) {
        return position.distance(new Vector3f(graphicSensor.getPosition()));
    }

    @Override
    public void setSink(boolean x) {
        super.setSink(x);
        geometry.getMaterial().setColor("Color", ColorRGBA.Red);
    }

}
