package app.stats;

public class StatsSample implements Sample {
    int i;

    public StatsSample(int i) {
        this.i = i;
    }

    @Override
    public String toString() {
        return Integer.toString(i);
    }
}
