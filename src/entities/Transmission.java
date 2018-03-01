package entities;

public class Transmission implements TransmissionListener {
    private Sensor sender;
    private Sensor receiver;
    private double snr;

    public Transmission(Sensor sender, Sensor receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    @Override
    public void updateSnr(double x) {

    }
}
