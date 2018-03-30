package app;

import com.jme3.app.SimpleApplication;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;

import java.util.ArrayList;

import static app.H2OSim.FINISHED;

public class Demo extends SimpleApplication {

    private ArrayList<Geometry> array_sphere;
    private ArrayList<Geometry> array_line;
    private boolean charged = false;
    Vector3f campo;

    public void simpleInitApp() {
        campo = new Vector3f(100, 30, 100);
        DirectionalLight dl = new DirectionalLight();
        dl.setColor(ColorRGBA.White);
        dl.setDirection(new Vector3f(-.5f, -.5f, -.5f).normalizeLocal());
        rootNode.addLight(dl);

        Node pivot = new Node("pivot");
        rootNode.attachChild(pivot); // put this node in the scene

        Vector3f cam_position = new Vector3f(0, 0, 50);
        cam.setLocation(cam_position);
        flyCam.setMoveSpeed(8);

        array_sphere = new ArrayList<>();
        array_line = new ArrayList<>();

        for (int i = 0; i < 60; i++) {
            Vector3f sphere_position = new Vector3f(random(-campo.x, campo.x), random(-campo.y, campo.y), random(-campo.z, campo.z));
            Geometry sphere = sphereWithTexture(
                    30,
                    30,
                    0.1f,
                    sphere_position,
                    null,
                    new ColorRGBA(0f / 255f, 96f / 255f, 255f / 255f, 1f)
            );

            array_sphere.add(sphere);
        }


        for (Geometry sphere_1 : array_sphere) {
            for (Geometry sphere_2 : array_sphere) {
                array_line.add(
                        linkBetweenGeometries(sphere_1, sphere_2, new ColorRGBA(15f / 255f, 221f / 255f, 25f / 255f, 0.13f))
                );
            }
        }

        /* Attach the two boxes to the *pivot* node. (And transitively to the root node.) */
        for (Geometry sphere : array_sphere) {
            pivot.attachChild(sphere);
        }
        for (Geometry line : array_line) {
            pivot.attachChild(line);
        }
        grid();
        charged = true;
    }

    private float random(float min, float max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    private Geometry sphereWithTexture(int zSamples, int radialSamples, float raggio, Vector3f posizione, String texturePath, ColorRGBA colore) {

        Sphere sphere = new Sphere(zSamples, radialSamples, raggio);
        Geometry sphere_geometry = new Geometry("Sphere", sphere);

        sphere_geometry.setLocalTranslation(posizione);

        Material sphere_material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

        if (texturePath != null) {
            Texture sphere_texture = assetManager.loadTexture(texturePath);
            sphere_material.setTexture("ColorMap", sphere_texture);
        } else {
            sphere_material.setColor("Color", colore);
        }

        sphere_geometry.setMaterial(sphere_material);
        sphere_geometry.updateModelBound();

        return sphere_geometry;
    }

    private Geometry linkBetweenGeometries(Geometry g1, Geometry g2, ColorRGBA color) {
        Vector3f position_1 = g1.getLocalTranslation();
        Vector3f position_2 = g2.getLocalTranslation();

        Mesh lineMesh = new Mesh();
        lineMesh.setMode(Mesh.Mode.Lines);
        lineMesh.setBuffer(VertexBuffer.Type.Position, 3, new float[]{position_1.x, position_1.y, position_1.z, position_2.x, position_2.y, position_2.z});
        lineMesh.setBuffer(VertexBuffer.Type.Index, 2, new short[]{0, 1});

        Geometry lineGeometry = new Geometry("line", lineMesh);
        Material lineMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

        lineMaterial.setColor("Color", color);
        lineMaterial.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);

        lineGeometry.setMaterial(lineMaterial);
        lineGeometry.updateModelBound();
        lineGeometry.setQueueBucket(RenderQueue.Bucket.Translucent);

        return lineGeometry;
    }

    private void grid() {
        for (float i = -campo.x; i <= campo.x; i += campo.x / 10) {
            for (float j = -campo.y; j <= campo.y; j += campo.y / 10) {
                rootNode.attachChild(line(
                        new Vector3f(i, j, -campo.z),
                        new Vector3f(i, -j, -campo.z),
                        new ColorRGBA(229, 239, 255, 0.001f))
                );
                rootNode.attachChild(line(
                        new Vector3f(-i, j, -campo.z),
                        new Vector3f(i, j, -campo.z),
                        new ColorRGBA(229, 239, 255, 0.001f))
                );

            }
        }
        for (float i = -campo.y; i <= campo.y; i += campo.y / 10) {
            for (float j = -campo.z; j <= campo.z; j += campo.z / 10) {
                rootNode.attachChild(line(
                        new Vector3f(campo.x, i, j),
                        new Vector3f(campo.x, i, -j),
                        new ColorRGBA(229, 239, 255, 0.001f))
                );
                rootNode.attachChild(line(
                        new Vector3f(campo.x, -i, j),
                        new Vector3f(campo.x, i, j),
                        new ColorRGBA(229, 239, 255, 0.001f))
                );
            }
        }
        for (float i = -campo.x; i <= campo.x; i += campo.x / 10) {
            for (float j = -campo.z; j <= campo.z; j += campo.z / 10) {
                rootNode.attachChild(line(
                        new Vector3f(i, -campo.y, j),
                        new Vector3f(-i, -campo.y, j),
                        new ColorRGBA(229, 239, 255, 0.001f))
                );
                rootNode.attachChild(line(
                        new Vector3f(i, -campo.y, -j),
                        new Vector3f(i, -campo.y, j),
                        new ColorRGBA(229, 239, 255, 0.001f))
                );
            }
        }

    }

    private Geometry line(Vector3f inizio, Vector3f fine, ColorRGBA colore) {
        Mesh lineMesh = new Mesh();
        lineMesh.setMode(Mesh.Mode.Lines);
        lineMesh.setBuffer(VertexBuffer.Type.Position, 3, new float[]{inizio.x, inizio.y, inizio.z, fine.x, fine.y, fine.z});
        lineMesh.setBuffer(VertexBuffer.Type.Index, 2, new short[]{0, 1});

        Geometry lineGeometry = new Geometry("line", lineMesh);
        Material lineMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");

        lineMaterial.setColor("Color", colore);
        lineGeometry.setMaterial(lineMaterial);
        lineGeometry.updateModelBound();
        return lineGeometry;
    }

    public ArrayList<Geometry> getArray_sphere() {
        return array_sphere;
    }

    public ArrayList<Geometry> getArray_line() {
        return array_line;
    }

    public boolean isCharged() {
        return charged;
    }
}
