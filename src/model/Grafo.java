package model;

import auxiliares.LeitorCSV;

import java.io.IOException;
import java.util.*;

public class Grafo {
    private Map<String, Aeroporto> aeroportos;

    public Grafo(String aerodromos, String aeronaves, String cias, String voos) throws IOException {
        aeroportos = new HashMap<>();
        LeitorCSV.carregarArquivos(this, aerodromos, aeronaves, cias, voos);
    }

    public Map<String, Aeroporto> getAeroportos(){
        return aeroportos;
    }

    public void setAeroportos(Map<String, Aeroporto> aeroportos) {
        this.aeroportos = aeroportos;
    }

    public List<Aeroporto> getTop5Graus() {
        return aeroportos.values().stream().sorted(Comparator.comparing(Aeroporto::getGrauTotal).reversed()).limit(5).toList();
    }
}
