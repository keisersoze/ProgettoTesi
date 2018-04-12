package app.stats.impl;

import app.stats.Collector;
import app.stats.Sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseCollector implements Collector {
    private Map<String, List<Sample>> sources;

    public BaseCollector () {
        sources = new HashMap<>();

    }


    public void addStatSource (String source) {
        sources.put(source, new ArrayList<Sample>());
    }

    public List<Sample> getSourceSamples (String source) {
        return sources.get(source);
    }

    public void update (String source, Sample sample) {
        sources.get(source).add(sample);
    }
}
