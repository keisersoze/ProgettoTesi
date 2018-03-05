package evt;

public abstract class BaseEvent implements Event,Comparable<Event> {
    double remainingTime;

    public BaseEvent(double remainingTime) {
        this.remainingTime = remainingTime;
    }

    public double getPriority() {
        return remainingTime;
    }

    @Override
    public int compareTo(Event o) {
        return Double.compare(this.remainingTime,o.getRemainingTime());
    }

    @Override
    public double getRemainingTime() {
        return remainingTime;
    }

}
