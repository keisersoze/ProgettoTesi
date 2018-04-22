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
import app.sim.MyLib;

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
        } else { return null; }

    }

    private List<Sensor> layerDeploymentInverted () {
        List<Sensor> sensors = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Sensor s1 = getSensor(MyLib.random(0, (float) H20Sim.FIELD_X), MyLib.random(0, 10), MyLib.random(0, (float) H20Sim.FIELD_Z));
            sensors.add(s1);
        }

        for (int i = 0; i < 50; i++) {
            Sensor s1 = getSensor(MyLib.random(0, (float) H20Sim.FIELD_X), MyLib.random(30, 36), MyLib.random(0, (float) H20Sim.FIELD_Z));
            sensors.add(s1);
        }

        for (int i = 0; i < 20; i++) {
            Sensor s1 = getSensor(MyLib.random(0, (float) H20Sim.FIELD_X), MyLib.random(63, 69), MyLib.random(0, (float) H20Sim.FIELD_Z));
            sensors.add(s1);
        }

        deploySink(sensors);
        return sensors;
    }

    private List<Sensor> layerDeployment () {
        List<Sensor> sensors = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Sensor s1 = getSensor(MyLib.random(0, MyLib.random(0, (float) H20Sim.FIELD_X)), MyLib.random(0, 10), MyLib.random(0, (float) H20Sim.FIELD_Z));
            sensors.add(s1);
        }

        for (int i = 0; i < 100; i++) {
            Sensor s1 = getSensor(MyLib.random(0, (float) H20Sim.FIELD_X), MyLib.random(30, 40), MyLib.random(0, (float) H20Sim.FIELD_Z));
            sensors.add(s1);
        }

        for (int i = 0; i < 100; i++) {
            Sensor s1 = getSensor(MyLib.random(0, (float) H20Sim.FIELD_X), MyLib.random(60, 70), MyLib.random(0, (float) H20Sim.FIELD_Z));
            sensors.add(s1);
        }

        deploySink(sensors);

        return sensors;
    }

    private void deploySink (List<Sensor> sensors) {
        for (int i = 30; i <= H20Sim.FIELD_X; i += 60) {
            for (int j = 30; j <= H20Sim.FIELD_Z; j += 60) {
                Sensor s1 = getSensor(i, 100, j);
                s1.setSink(true);
                sensors.add(s1);
            }
        }
    }

    private List<Sensor> baseDeployment () {
        List<Sensor> sensors = new ArrayList<>();

        for (int i = 0; i < 300; i++) {
            Sensor s1 = getSensor(MyLib.random(0, (float) H20Sim.FIELD_X), MyLib.random(0, 90), MyLib.random(0, (float) H20Sim.FIELD_Z));
            sensors.add(s1);
        }

        deploySink(sensors);

        return sensors;
    }

}
