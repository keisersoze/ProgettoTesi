package app.model.jme3.impl;

import app.Canvas;
import app.model.impl.AbstractSensor;
import app.model.jme3.GraphicSensor;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class JmeSensor extends AbstractSensor implements GraphicSensor {

    private Geometry geometry;
    private Canvas canvas;

    public JmeSensor (float x, float y, float z) {
        super(x, y, z);

    }

    @Override
    public void draw (Canvas canvas) {
        Vector3f position = new Vector3f(getX(), getY(), getZ());
        this.canvas = canvas;
        /*try {
            geometry = canvas.enqueue(() -> canvas.sphereWithTexture(this)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }*/

    }

    @Override
    public Geometry getGeometry () {
        return geometry;
    }

    @Override
    public void updatePosition () {
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
    public void setSink (boolean x) {
        super.setSink(x);
        geometry.getMaterial().setColor("Color", ColorRGBA.Red);
    }


}
