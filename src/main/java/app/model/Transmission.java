package app.model;

public interface Transmission {

    Frame getFrame ();

    void updateSnr (double x);

    boolean isSuccessfull ();

    void setSuccessfull (boolean x);

    Sensor getSender ();

    Sensor getReceiver ();

    double getSnr ();

    int getHop ();

    double getTime ();

    void setTime (double time);

    boolean isArrived ();

    void setArrived (boolean arrived);
}
