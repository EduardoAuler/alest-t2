package model;

import auxiliares.LeitorCSV;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
}
