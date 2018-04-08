package app.model;

public interface Transmission <S extends Sensor>{

    public void updateSnr(double x);

    boolean isSuccessfull();

    public void setSuccessfull(boolean x);

    public double getSnr();

    public S getSender();

    public S getReceiver();

}
