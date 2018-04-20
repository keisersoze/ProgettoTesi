package app.factory.h20;

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
            Sensor s1 = getSensor(MyLib.random(0, 200), MyLib.random(0, 10), MyLib.random(0, 200));
            sensors.add(s1);
        }

        for (int i = 0; i < 50; i++) {
            Sensor s1 = getSensor(MyLib.random(0, 200), MyLib.random(30, 36), MyLib.random(0, 200));
            sensors.add(s1);
        }

        for (int i = 0; i < 20; i++) {
            Sensor s1 = getSensor(MyLib.random(0, 200), MyLib.random(63, 69), MyLib.random(0, 200));
            sensors.add(s1);
        }

        for (int i = 30; i <= 170; i += 60) {
            for (int j = 30; j <= 170; j += 60) {
                Sensor s1 = getSensor(i, 100, j);
                s1.setSink(true);
                sensors.add(s1);
            }
        }
        return sensors;
    }

    private List<Sensor> layerDeployment () {
        List<Sensor> sensors = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Sensor s1 = getSensor(MyLib.random(0, 200), MyLib.random(15, 25), MyLib.random(0, 200));
            sensors.add(s1);
        }

        for (int i = 0; i < 100; i++) {
            Sensor s1 = getSensor(MyLib.random(0, 200), MyLib.random(35, 45), MyLib.random(0, 200));
            sensors.add(s1);
        }

        for (int i = 0; i < 100; i++) {
            Sensor s1 = getSensor(MyLib.random(0, 200), MyLib.random(55, 65), MyLib.random(0, 200));
            sensors.add(s1);
        }

        for (int i = 0; i < 100; i++) {
            Sensor s1 = getSensor(MyLib.random(0, 200), MyLib.random(75, 85), MyLib.random(0, 200));
            sensors.add(s1);
        }

        for (int i = 0; i < 10; i++) {
            Sensor s1 = getSensor(MyLib.random(0, 200), 100, MyLib.random(0, 200));
            s1.setSink(true);
            sensors.add(s1);
        }
        return sensors;
    }

    private List<Sensor> baseDeployment () {
        List<Sensor> sensors = new ArrayList<>();

        for (int i = 0; i < 500; i++) {
            Sensor s1 = getSensor(MyLib.random(0, 200), MyLib.random(0, 80), MyLib.random(0, 200));
            sensors.add(s1);
        }

        for (int i = 0; i < 10; i++) {
            Sensor s1 = getSensor(MyLib.random(0, 200), 100, MyLib.random(0, 200));
            s1.setSink(true);
            sensors.add(s1);
        }

        return sensors;
    }

}
