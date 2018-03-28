package app;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;

import java.util.ArrayList;

public class Demo extends SimpleApplication {

    public void simpleInitApp() {

        DirectionalLight dl = new DirectionalLight();
        dl.setColor(ColorRGBA.White);
        dl.setDirection(new Vector3f(2.8f, -2.8f, -2.8f).normalizeLocal());
        rootNode.addLight(dl);

        ArrayList<Geometry> array_sphere = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            Sphere temp_sphere = new Sphere(30, 30, 0.1f);
            Geometry sphere_geometry = new Geometry("Sphere", temp_sphere);
            sphere_geometry.setLocalTranslation(new Vector3f(random(-10, 10), random(-10, 10), random(-10, 10)));

            Material sphere_material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            Texture sphere_texture = assetManager.loadTexture("Textures/earth.jpg");
            sphere_material.setTexture("ColorMap", sphere_texture);

            sphere_geometry.setMaterial(sphere_material);
            array_sphere.add(sphere_geometry);
        }



        /* Create a pivot node at (0,0,0) and attach it to the root node */
        Node pivot = new Node("pivot");
        rootNode.attachChild(pivot); // put this node in the scene

        /* Attach the two boxes to the *pivot* node. (And transitively to the root node.) */
        for (Geometry geometry : array_sphere) {
            pivot.attachChild(geometry);
        }
        /* Rotate the pivot node: Note that both boxes have rotated! */
        //pivot.rotate(.4f,.4f,0f);
    }

    private float random(int min, int max) {
        return (float) min + (int) (Math.random() * ((max - min) + 1));
    }

}
