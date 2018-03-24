package evt;

import evt.actions.Action;

import java.util.ArrayList;
import java.util.List;

public class BaseEvent implements Event,Comparable<Event> {
    private double time;
    private List<Action> actionList;

    public BaseEvent(double time) {
        this.time = time;
        actionList = new ArrayList();
    }

    /**
     * confronta la tempo di se stesso con quello di {@code o}
     * @param o evento sul quale è operato il confronto
     * @return un valore che indica l'esito del confronto
     */

    public int compareTo(Event o) {
        return Double.compare(this.getTime(),o.getTime());
    }

    /**
     * restituisce
     * @return la proprità time
     */

    public double getTime() {
        return time;
    }

    /**
     *
     */

    public void tick() {
        for (Action l: actionList) {
            l.update();
        }
    }

    /**
     *
     * @param l
     */

    public void addListener(Action l) {
        actionList.add(l);
    }
}
