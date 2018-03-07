package evt.listeners;

import evt.Event;
import model.Sensor;

import java.util.List;

public class UpdateSNR implements Listener {
    private List<Sensor> sensors;

    public UpdateSNR(List<Sensor> sensors) {
        this.sensors = sensors;
    }

    @Override
    public void execute(Event event) {

    }
}
