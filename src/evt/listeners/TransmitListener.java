package evt.listeners;

import evt.Event;
import evt.StartOfTransmissionEvent;

public class TransmitListener implements Listener{
    @Override
    public void execute(Event e) {
        StartOfTransmissionEvent e2 = (StartOfTransmissionEvent) e; //TODO no
    }
}
