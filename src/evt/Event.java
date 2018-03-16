
package evt;

import evt.listeners.Listener;

public interface Event {

    void addListener(Listener listener);
    /**
     * chiama tutte le callback associate all'evento
     */
    void tick();

    /**
     * restituisce un valore che indica la priorità dell'evento
     * @return il valore che indica la priorità
     */
    double getTime();

}

