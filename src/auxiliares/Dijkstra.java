package auxiliares;

import model.Aeroporto;
import model.Grafo;
import model.RespostaDijkstra;
import model.Voo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class Dijkstra {

    private final Grafo grafo;

    private Map<Aeroporto, LocalDateTime> dist;
    private Map<Aeroporto, Voo> edgeTo;

    private static class Estado {

        private final Aeroporto aeroporto;
        private final LocalDateTime horario;

        public Estado(Aeroporto aeroporto,
                      LocalDateTime horario) {

            this.aeroporto = aeroporto;
            this.horario = horario;
        }

        public Aeroporto getAeroporto() {
            return aeroporto;
        }

        public LocalDateTime getHorario() {
            return horario;
        }
    }

    public Dijkstra(Grafo grafo) {
        this.grafo = grafo;
    }

    public List<Voo> menorCaminho(
            String origemCodigo,
            String destinoCodigo,
            LocalDateTime inicio,
            String aeroportoFechadoCodigo) {

        // verifica se o aeroporto de origem ou o de destino estão fechados
        if (origemCodigo.equals(aeroportoFechadoCodigo) || destinoCodigo.equals(aeroportoFechadoCodigo)) {
            return Collections.emptyList(); 
        }

        Aeroporto origem = findAeroportoByCodigo(origemCodigo);
        Aeroporto destino = findAeroportoByCodigo(destinoCodigo);
        Aeroporto aeroportoFechado = findAeroportoByCodigo(aeroportoFechadoCodigo);

        dist = new HashMap<>();
        edgeTo = new HashMap<>();

        PriorityQueue<Estado> fila =
                new PriorityQueue<>(
                        Comparator.comparing(
                                Estado::getHorario
                        )
                );

        dist.put(origem, inicio);

        fila.add(
                new Estado(origem, inicio)
        );

        while (!fila.isEmpty()) {

            Estado estadoAtual = fila.poll();

            Aeroporto atual =
                    estadoAtual.getAeroporto();

            LocalDateTime horarioAtual =
                    estadoAtual.getHorario();

            // ignora estados antigos
            if (!horarioAtual.equals(dist.get(atual)))
                continue;

            if (atual.equals(destino))
                break;

            for (Voo voo : atual.getVoosSaida()) {

                Aeroporto prox =
                        voo.getDestino();

                // aeroporto fechado
                if (aeroportoFechado != null &&
                        prox.equals(aeroportoFechado) || voo.getOrigem().equals(aeroportoFechado))
                    continue;

                LocalDateTime horarioDisponivel =
                        horarioAtual;

                // conexão
                if (!atual.equals(origem)) {

                    long tempoConexao =
                            ehHub(atual)
                                    ? 60
                                    : 45;

                    horarioDisponivel =
                            horarioDisponivel.plusMinutes(
                                    tempoConexao
                            );
                }

                // perdeu o voo
                if (voo.getPartida()
                        .isBefore(horarioDisponivel))
                    continue;

                LocalDateTime novaChegada =
                        voo.getChegada();

                if (!dist.containsKey(prox)
                        || novaChegada.isBefore(
                        dist.get(prox))) {

                    dist.put(
                            prox,
                            novaChegada
                    );

                    edgeTo.put(
                            prox,
                            voo
                    );

                    fila.add(
                            new Estado(
                                    prox,
                                    novaChegada
                            )
                    );
                }
            }
        }

        return reconstruirCaminho(
                origem,
                destino
        );
    }

    private List<Voo> reconstruirCaminho(
            Aeroporto origem,
            Aeroporto destino) {

        LinkedList<Voo> caminho =
                new LinkedList<>();

        Aeroporto atual = destino;

        while (!atual.equals(origem)) {

            Voo voo =
                    edgeTo.get(atual);

            if (voo == null)
                return Collections.emptyList();

            caminho.addFirst(voo);

            atual = voo.getOrigem();
        }

        return caminho;
    }

    private boolean ehHub(Aeroporto aeroporto) {

        return grafo
                .getTop5Graus()
                .contains(aeroporto);
    }

    private Aeroporto findAeroportoByCodigo(String codigo){
        return grafo.getAeroportos().get(codigo);
    }

    public RespostaDijkstra obterRelatorio(List<Voo> voos){
        if (voos.isEmpty()) return null;

        Map<String, Integer> tempoNoAeroporto = new HashMap<>();

        // calcula o tempo total do primeiro voo até a chegada
        LocalDateTime inicioJornada = voos.get(0).getPartida();
        LocalDateTime fimJornada = voos.get(voos.size() - 1).getChegada();
        Duration tempoTotal = Duration.between(inicioJornada, fimJornada);

        // calcula o tempo total no solo
        for(int i = 0; i <voos.size() - 1; i++){
                Voo vooAtual = voos.get(i);
                Voo proximoVoo = voos.get(i+1);

                long minutosEmSolo = Duration.between(vooAtual.getChegada(), proximoVoo.getPartida()).toMinutes();
                tempoNoAeroporto.put(vooAtual.getDestino().getCodigo(), (int) minutosEmSolo);
        }

        return new RespostaDijkstra(voos, tempoNoAeroporto, tempoTotal);
    }

}
