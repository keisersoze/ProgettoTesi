package evt;

import evt.listeners.Listener;

import java.util.ArrayList;
import java.util.List;

public class BaseEvent implements Event,Comparable<Event> {
    private double time;
    private List<Listener> listenerList;

    public BaseEvent(double time) {
        this.time = time;
        listenerList = new ArrayList<>();
    }

    /**
     * confronta la tempo di se stesso con quello di {@code o}
     * @param o evento sul quale è operato il confronto
     * @return un valore che indica l'esito del confronto
     */
    @Override
    public int compareTo(Event o) {
        return Double.compare(this.getTime(),o.getTime());
    }

    /**
     * restituisce
     * @return la proprità time
     */
    @Override
    public double getTime() {
        return time;
    }

    /**
     *
     */
    @Override
    public void tick() {
        for (Listener l:listenerList) {
            l.execute();
        }
    }

    /**
     *
     * @param l
     */
    @Override
    public void addListener(Listener l) {
        listenerList.add(l);
    }
}
