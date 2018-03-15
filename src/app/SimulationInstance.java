package app;

import evt.DefaultScheduler;
import evt.StartOfTransmissionEvent;
import model.BaseSensor;

public class SimulationInstance implements Runnable{

    @Override
    public void run() {
        DefaultScheduler scheduler = new DefaultScheduler();
        BaseSensor s1= new BaseSensor();
        StartOfTransmissionEvent e1= new StartOfTransmissionEvent(2,s1);
        StartOfTransmissionEvent e2= new StartOfTransmissionEvent(1.5,s1);
        StartOfTransmissionEvent e3= new StartOfTransmissionEvent(0.74,s1);
        StartOfTransmissionEvent e4= new StartOfTransmissionEvent(0.2,s1);
        scheduler.addEvent(e1);
        scheduler.addEvent(e2);
        scheduler.addEvent(e3);
        scheduler.addEvent(e4);
        System.out.println(scheduler.scheduleEvent().getPriority());
        System.out.println(scheduler.scheduleEvent().getPriority());
        System.out.println(scheduler.scheduleEvent().getPriority());
        System.out.println(scheduler.scheduleEvent().getPriority());
    }
}
