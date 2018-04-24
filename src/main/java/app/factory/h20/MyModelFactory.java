package app.factory.h20;

import app.H20Sim;
import app.factory.DeploymentTypes;
import app.factory.ModelFactory;
import app.model.Frame;
import app.model.Sensor;
import app.model.Transmission;
import app.model.h20.BaseFrame;
import app.model.h20.BaseSensor;
import app.model.h20.BaseTransmission;
import app.utils.MyLib;

import java.util.ArrayList;
import java.util.List;

public class MyModelFactory implements ModelFactory {
    @Override
    public Sensor getSensor (float x, float y, float z) {
        return new BaseSensor(x, y, z);
    }

    @Override
    public Transmission getTransmission (Sensor sender, Sensor receiver, Frame frame, int hop) {
        return new BaseTransmission(sender, receiver, frame, hop);
    }

    @Override
    public Frame getFrame (double size, Sensor owner, double arrivalTime) {
        return new BaseFrame(size, owner, arrivalTime);
    }

    @Override
    public List<Sensor> deploySensors (String deploymentType) {
        if (deploymentType.equalsIgnoreCase(DeploymentTypes.BaseDeployment)) {
            return baseDeployment();
        } else if (deploymentType.equalsIgnoreCase(DeploymentTypes.LayerDeployment)) {
            return layerDeployment();
        } else if (deploymentType.equalsIgnoreCase(DeploymentTypes.LayerProportionalDeployment)) {
            return layerDeploymentInverted();
        }if (deploymentType.equalsIgnoreCase(DeploymentTypes.OneSDeployment)) {
            return oneSDeployment();
        } else { return null; }

    }

    private List<Sensor> oneSDeployment() {
        List<Sensor> sensors = new ArrayList<>();
        sensors.add(getSensor(0,0,0));
        return sensors;
    }

    private List<Sensor> layerDeploymentInverted () {
        List<Sensor> sensors = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            Sensor s1 = getSensor(MyLib.random(0, H20Sim.FIELD_X), MyLib.random(0, H20Sim.FIELD_Y / 10), MyLib.random(0, H20Sim.FIELD_Z));
            sensors.add(s1);
        }

        for (int i = 0; i < 20; i++) {
            Sensor s1 = getSensor(MyLib.random(0, H20Sim.FIELD_X), MyLib.random(H20Sim.FIELD_Y / 2.5f, H20Sim.FIELD_Y / 2f), MyLib.random(0, H20Sim.FIELD_Z));
            sensors.add(s1);
        }

        for (int i = 0; i < 10; i++) {
            Sensor s1 = getSensor(MyLib.random(0, H20Sim.FIELD_X), MyLib.random(H20Sim.FIELD_Y / 1.5f, H20Sim.FIELD_Y / 1.35f), MyLib.random(0, H20Sim.FIELD_Z));
            sensors.add(s1);
        }

        deploySink(sensors);
        return sensors;
    }

    private List<Sensor> layerDeployment () {
        List<Sensor> sensors = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Sensor s1 = getSensor(MyLib.random(0, MyLib.random(0, H20Sim.FIELD_X)), MyLib.random(0, H20Sim.FIELD_Y / 10), MyLib.random(0, H20Sim.FIELD_Z));
            sensors.add(s1);
        }

        for (int i = 0; i < 100; i++) {
            Sensor s1 = getSensor(MyLib.random(0, H20Sim.FIELD_X), MyLib.random(H20Sim.FIELD_Y / 2.75f, H20Sim.FIELD_Y / 2.5f), MyLib.random(0, H20Sim.FIELD_Z));
            sensors.add(s1);
        }

        for (int i = 0; i < 100; i++) {
            Sensor s1 = getSensor(MyLib.random(0, H20Sim.FIELD_X), MyLib.random(H20Sim.FIELD_Y / 1.5f, H20Sim.FIELD_Y / 1.35f), MyLib.random(0, H20Sim.FIELD_Z));
            sensors.add(s1);
        }

        deploySink(sensors);

        return sensors;
    }

    private void deploySink (List<Sensor> sensors) {
        int startX = (int) H20Sim.FIELD_X / 10;
        int endX = (int) (H20Sim.FIELD_X - H20Sim.FIELD_X / 10);
        int startZ = (int) H20Sim.FIELD_Z / 10;
        int endZ = (int) (H20Sim.FIELD_Z - H20Sim.FIELD_Z / 10);
        int offsetX = (int) H20Sim.FIELD_X / 4;
        int offsetZ = (int) H20Sim.FIELD_Z / 4;

        for (int i = startX; i <= endX; i += offsetX) {
            for (int j = startZ; j <= endZ; j += offsetZ) {
                Sensor sensor = getSensor(i, H20Sim.FIELD_Y, j);
                sensor.setSink(true);
                sensors.add(sensor);
            }
        }
    }

    private List<Sensor> baseDeployment () {
        List<Sensor> sensors = new ArrayList<>();

        for (int i = 0; i < 200; i++) {
            Sensor s1 = getSensor(MyLib.random(0, H20Sim.FIELD_X), MyLib.random(0, H20Sim.FIELD_Y - H20Sim.FIELD_Y / 10), MyLib.random(0, H20Sim.FIELD_Z));
            sensors.add(s1);
        }

        deploySink(sensors);

        return sensors;
    }

}
