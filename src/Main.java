import auxiliares.LeitorCSV;
import model.Aeroporto;
import model.Grafo;

import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {
        Grafo g = new Grafo("C:\\Users\\dinho\\IdeaProjects\\t2-alest-eu\\src\\T2_Dados\\aerodromos.csv",
                "C:\\Users\\dinho\\IdeaProjects\\t2-alest-eu\\src\\T2_Dados\\aeronaves.csv",
                "C:\\Users\\dinho\\IdeaProjects\\t2-alest-eu\\src\\T2_Dados\\cias.csv",
                "C:\\Users\\dinho\\IdeaProjects\\t2-alest-eu\\src\\T2_Dados\\voos_mar2026.csv");


    }


}
