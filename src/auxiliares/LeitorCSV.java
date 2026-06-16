package auxiliares;

import model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class LeitorCSV {

    private static Map<String, Companhia> companhias = new HashMap<>();
    private static Map<String, Aeronave> aerovanes = new HashMap<>();
    private static Map<String, Aeroporto> aeroportos = new HashMap<>();

    public static void carregarArquivos(Grafo g, String aerodromos, String aeronaves, String cias, String voos) throws IOException {
        carregaCompanhias(cias);
        carregaAeronaves(aeronaves);
        carregaAeroportos(aerodromos);
        carregaVoos(voos);
        g.setAeroportos(aeroportos);
    }


    private static Map<String, Companhia> carregaCompanhias(String arquivo) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(arquivo));
        br.readLine();


        String linha;

        while ((linha = br.readLine()) != null) {

            String[] campos = linha.split(";");

            String codigo = campos[0];
            String nome = campos[2];
            String pais = campos[3];

            Companhia companhia = new Companhia(codigo, nome, pais);

            companhias.put(codigo, companhia);
        }

        br.close();

        return companhias;
    }

    public static Map<String, Aeronave> carregaAeronaves(String arquivo) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(arquivo));

        br.readLine();

        String linha;

        while ((linha = br.readLine()) != null) {

            String[] campos = linha.split(";");

            String codigo = campos[0];
            String modelo = campos[2];

            Aeronave aeronave = new Aeronave(codigo, modelo);

            aerovanes.put(codigo, aeronave);
        }

        br.close();

        return aerovanes;
    }


    public static Map<String, Aeroporto> carregaAeroportos(String arquivo) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(arquivo));

        br.readLine();

        String linha;

        while ((linha = br.readLine()) != null) {

            String[] campos = linha.split(";");

            String codigo = campos[0];
            String nome = campos[2];

            Aeroporto aeroporto = new Aeroporto(codigo, nome);

            aeroportos.put(codigo, aeroporto);
        }

        br.close();

        return aeroportos;
    }

    public static void carregaVoos(String arquivo) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(arquivo));

        br.readLine();

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        String linha;

        while ((linha = br.readLine()) != null) {

            linha = linha.replace("\"", "");

            String[] campos = linha.split(",");

            String numero = campos[5];

            Aeroporto origem =
                    aeroportos.get(campos[10]);

            Aeroporto destino =
                    aeroportos.get(campos[9]);

            Companhia companhia =
                    companhias.get(campos[7]);

            Aeronave aeronave =
                    aerovanes.get(campos[8]);

            if (origem == null || destino == null || companhia == null || aeronave == null) continue;


            LocalDateTime chegada =
                    LocalDateTime.parse(campos[1], formatter);

            LocalDateTime partida =
                    LocalDateTime.parse(campos[2], formatter);

            long duracao =
                    Duration.between(partida, chegada).toMinutes();

            /*
             * Segurança caso exista algum voo
             * que atravesse a meia-noite e venha
             * com data inconsistente.
             */
            if (duracao < 0) {

                chegada = chegada.plusDays(1);

                duracao =
                        Duration.between(partida, chegada)
                                .toMinutes();
            }

            Voo voo = new Voo(
                    numero,
                    origem,
                    destino,
                    partida,
                    chegada,
                    companhia,
                    aeronave,
                    duracao
            );


            origem.addVoo(voo);
        }
        br.close();

    }







}
