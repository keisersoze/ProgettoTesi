package app.stats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SamplesCollector {
    private Map <String,List<Sample>> sources;

    public SamplesCollector() {
        sources = new HashMap ();

    }


    public void addStatSource(String source){
        sources.put(source,new ArrayList<Sample>());
    }

    public List<Sample> getSourceSamples(String source ){
        return sources.get(source);
    }

    public synchronized void update(String source, Sample sample){
        sources.get(source).add(sample);
    }
}
