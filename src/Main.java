import auxiliares.Dijkstra;
import model.Aeroporto;
import model.Grafo;
import model.RespostaDijkstra;
import model.Voo;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        try {
            System.out.println("====================================================");
            System.out.println("   Carregando a Malha Aérea Brasileira (ANAC)...    ");
            System.out.println("====================================================");
            
            // Lembre-se de ajustar para caminhos relativos ou caminhos válidos na sua máquina
            Grafo g = new Grafo(
                    "C:\\Users\\leona\\Downloads\\Alest2TF\\alest-t2\\src\\T2_Dados\\aerodromos.csv",
                    "C:\\Users\\leona\\Downloads\\Alest2TF\\alest-t2\\src\\T2_Dados\\aeronaves.csv",
                    "C:\\Users\\leona\\Downloads\\Alest2TF\\alest-t2\\src\\T2_Dados\\cias.csv",
                    "C:\\Users\\leona\\Downloads\\Alest2TF\\alest-t2\\src\\T2_Dados\\voos_mar2026.csv"
            );

            System.out.println("\n--- [ETAPA 2] 5 PRINCIPAIS HUBS ESTRUTURAIS ---");
            List<Aeroporto> hubs = g.getTop5Graus();
            for (int i = 0; i < hubs.size(); i++) {
                Aeroporto hub = hubs.get(i);
                System.out.printf("%d. %s - %s (Total de Conexões: %d)\n", 
                        (i + 1), hub.getCodigo(), hub.getNome(), hub.getGrauTotal());
            }
            System.out.println("-----------------------------------------------\n");

            // Solicitação de dados do usuário
            System.out.print("Digite o código ICAO do aeroporto de ORIGEM (ex: SBPA): ");
            String origem = scanner.nextLine().toUpperCase().trim();

            System.out.print("Digite o código ICAO do aeroporto de DESTINO (ex: SBGR): ");
            String destino = scanner.nextLine().toUpperCase().trim();

            System.out.print("Digite a data e horário de partida (formato: dd/MM/yyyy HH:mm): ");
            String dataTexto = scanner.nextLine().trim();
            LocalDateTime horarioPartida = LocalDateTime.parse(dataTexto, formatter);

            System.out.print("Deseja fechar algum dos 5 hubs por meteorologia? (S/N): ");
            String respostaFechar = scanner.nextLine().toUpperCase().trim();
            
            String hubFechado = null;
            if (respostaFechar.equals("S")) {
                System.out.print("Digite o código ICAO do hub a ser fechado: ");
                hubFechado = scanner.nextLine().toUpperCase().trim();
            }

            System.out.println("\nProcessando a melhor rota de tempo mínimo...\n");
            Dijkstra d = new Dijkstra(g);
            List<Voo> resultadoVoos = d.menorCaminho(origem, destino, horarioPartida, hubFechado);

            // Geração e exibição do relatório formatado para o vídeo
            RespostaDijkstra relatorio = d.obterRelatorio(resultadoVoos);

            if (relatorio == null || resultadoVoos.isEmpty()) {
                System.out.println("❌ Não foi possível encontrar uma rota válida entre esses aeroportos com as restrições de tempo dadas.");
            } else {
                System.out.println("====================================================");
                System.out.println("          RELATÓRIO DE ROTA ENCONTRADA              ");
                System.out.println("====================================================");
                
                long totalHoras = relatorio.tempoTotal.toHours();
                long totalMinutos = relatorio.tempoTotal.toMinutesPart();
                System.out.printf("⏱️ TEMPO TOTAL DE JORNADA: %dh %dmin\n\n", totalHoras, totalMinutos);

                System.out.println("✈️ ITINERÁRIO DETALHADO:");
                for (int i = 0; i < relatorio.voos.size(); i++) {
                    Voo v = relatorio.voos.get(i);
                    System.out.printf("  [%dº VOO] %s - Voo %s (%s)\n", (i + 1), v.getCompanhia().getNome(), v.getNumero(), v.getAeronave().getModelo());
                    System.out.printf("     Decolagem: %s de %s (%s)\n", v.getPartida().format(formatter), v.getOrigem().getCodigo(), v.getOrigem().getNome());
                    System.out.printf("     Pouso:     %s em %s (%s)\n", v.getChegada().format(formatter), v.getDestino().getCodigo(), v.getDestino().getNome());
                    System.out.printf("     Duração do voo: %d minutos\n", v.getDuracao());
                    
                    // Se houver uma próxima conexão, mostra o tempo de espera em solo calculado
                    if (i < relatorio.voos.size() - 1) {
                        String proxAeroporto = v.getDestino().getCodigo();
                        Integer tempoSolo = relatorio.tempoNoAeroporto.get(proxAeroporto);
                        System.out.printf("\n     ⏳ Conexão em %s: %d minutos em solo\n\n", proxAeroporto, tempoSolo);
                    }
                }
                System.out.println("====================================================");
            }

        } catch (Exception e) {
            System.err.println("❌ Erro durante a execução. Verifique o formato dos dados inseridos.");
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}