import events.Event;
import events.Scheduler;
import events.TrasmissionEvent;

public class Simulator {


    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();
        TrasmissionEvent e1= new TrasmissionEvent(2);
        scheduler.addEvent(e1);

    }
}
