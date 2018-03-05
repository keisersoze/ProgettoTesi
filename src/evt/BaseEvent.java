package evt;

public abstract class BaseEvent implements Event,Comparable<Event> {
    double remainingTime;

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

}
