import entities.Sensor;
import events.DefaultScheduler;
import events.StartOfTransmission;

public class Simulator {


    public static void main(String[] args) {
        DefaultScheduler scheduler = new DefaultScheduler();
        Sensor s1= new Sensor();
        StartOfTransmission e1= new StartOfTransmission(2,s1);
        StartOfTransmission e2= new StartOfTransmission(1.5,s1);
        StartOfTransmission e3= new StartOfTransmission(0.74,s1);
        StartOfTransmission e4= new StartOfTransmission(0.2,s1);
        scheduler.addEvent(e1);
        scheduler.addEvent(e2);
        scheduler.addEvent(e3);
        scheduler.addEvent(e4);
        System.out.println(scheduler.scheduleEvent().getRemainingTime());
        System.out.println(scheduler.scheduleEvent().getRemainingTime());
        System.out.println(scheduler.scheduleEvent().getRemainingTime());
        System.out.println(scheduler.scheduleEvent().getRemainingTime());
    }
}
