import auxiliares.Dijkstra;
import auxiliares.LeitorCSV;
import model.Aeroporto;
import model.Grafo;

import java.io.IOException;
import java.time.LocalDateTime;


public class Main {

    public static void main(String[] args) throws IOException {
        Grafo g = new Grafo("C:\\Users\\dinho\\Downloads\\Alest2\\alest-t2\\src\\T2_Dados\\aerodromos.csv",
                "C:\\Users\\dinho\\Downloads\\Alest2\\alest-t2\\src\\T2_Dados\\aeronaves.csv",
                "C:\\Users\\dinho\\Downloads\\Alest2\\alest-t2\\src\\T2_Dados\\cias.csv",
                "C:\\Users\\dinho\\Downloads\\Alest2\\alest-t2\\src\\T2_Dados\\voos_mar2026.csv");

        Dijkstra d = new Dijkstra(g);



        System.out.println(d.menorCaminho("KMCO", "KMEM", LocalDateTime.of(2026, 3, 3, 12,0), "SBKP"));
    }


}
