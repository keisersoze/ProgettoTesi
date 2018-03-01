
package events;

import entities.Sensor;

public interface Event {
    void tick();
    double getRemaingTime();

}

