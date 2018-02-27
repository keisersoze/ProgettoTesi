package events;

public abstract class AbstractEvent implements Event,Comparable<Event> {
    double remainingTime;

    public AbstractEvent(double remainingTime) {
        this.remainingTime = remainingTime;
    }

    public double getRemainingTime() {
        return remainingTime;
    }


}
