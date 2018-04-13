package app.core;

/**
 * Pattern strategy per lo scheduling
 */
public interface Scheduler {
    /**
     * @return restituisce un evento secondo le politiche di scheduling
     */
    Event scheduleEvent();

    /**
     * aggiunge {@code e} alla coda di eventi in attesa
     *
     * @param e elemento da aggiungere
     */
    void addEvent(Event e);

}
