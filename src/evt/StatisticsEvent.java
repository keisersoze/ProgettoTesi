package evt;

public class StatisticsEvent extends BaseEvent implements Event {

    public StatisticsEvent(double remainingTime) {
        super(remainingTime);
    }

    @Override
    public void tick() {
        super.tick();

    }
}
