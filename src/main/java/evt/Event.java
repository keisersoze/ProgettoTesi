
package evt;

import evt.actions.Action;

public interface Event {

    void addListener(Action action);
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

