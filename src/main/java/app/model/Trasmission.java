package app.model;

public interface Trasmission {
    public void updateSnr(double x);

    boolean isSuccessfull();

    public Sensor getSender();

    public Sensor getReceiver();

    public double getSnr();
}
