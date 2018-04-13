package app.stats;

import java.util.List;

public interface Collector {
    void addStatSource(String source);

    List<Sample> getSourceSamples(String source);

    void update(String source, Sample sample);
}
