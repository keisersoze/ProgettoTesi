
package evt;

import evt.listeners.Listener;

public interface Event {

    void addEventListener(Listener l);
    /**
     * chiama tutte le callback associate all'evento
     */
    void tick();

    /**
     * restituisce un valore che indica la priorità dell'evento
     * @return il valore che indica la priorità
     */
    double getPriority();

}

