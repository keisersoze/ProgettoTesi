package app.model;

public interface Trasmission {
    public void updateSnr(double x);

    boolean isSuccessfull();

    public void setSuccessfull(boolean x);

    public Sensor getSender();

    public Sensor getReceiver();

    public double getSnr();
}
