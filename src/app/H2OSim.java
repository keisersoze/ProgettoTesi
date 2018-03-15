package app;

public class H2OSim {
    private static H2OSim ourInstance = new H2OSim();

    public static H2OSim getInstance() {
        return ourInstance;
    }

    public static void main(String[] args) {
        int i;
        for (i=0 ; i<15 ; i++ ){
            new Thread(new SimulationInstance()).start();
        }
    }
}
