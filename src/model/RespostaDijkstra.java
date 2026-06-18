package model;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class RespostaDijkstra {

    public List<Voo> voos;
    public Map<String, Integer> tempoNoAeroporto;
    public Duration tempoTotal;

    public RespostaDijkstra(List<Voo> voos, Map<String, Integer> tempoNoAeroporto, Duration tempoTotal) {
        this.voos = voos;
        this.tempoNoAeroporto = tempoNoAeroporto;
        this.tempoTotal = tempoTotal;
    }
}