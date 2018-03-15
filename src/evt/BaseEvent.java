package evt;

import evt.listeners.Listener;

import java.util.List;

public class BaseEvent implements Event,Comparable<Event> {
    private double remainingTime;
    private List<Listener> listenerList;

    public BaseEvent(double remainingTime) {
        this.remainingTime = remainingTime;
    }

    /**
     * confronta la priorità di se stesso con quella di {@code o}
     * @param o evento con cui confrontare la priorità
     * @return un valore che indica l'esito del confronto
     */
    @Override
    public int compareTo(Event o) {
        return Double.compare(this.getPriority(),o.getPriority());
    }

    /**
     * restituisce come priorità il valore del proprio remainingTime
     * @return la proprità remainingTime
     */
    @Override
    public double getPriority() {
        return remainingTime;
    }

    /**
     *
     */
    @Override
    public void tick() {
        for (Listener l:listenerList) {
            l.execute(this);
        }
    }

    /**
     *
     * @param l
     */
    @Override
    public void addEventListener(Listener l) {
        listenerList.add(l);
    }
}
