package model;

import java.time.LocalDateTime;

public class Voo {

    private String numero;

    private Aeroporto origem;
    private Aeroporto destino;

    private LocalDateTime partida;
    private LocalDateTime chegada;

    private Companhia companhia;
    private Aeronave aeronave;

    private long duracao;

    public Voo(String numero, Aeroporto origem, Aeroporto destino, LocalDateTime partida,
               LocalDateTime chegada, Companhia companhia, Aeronave aeronave, long duracao) {
        this.numero = numero;
        this.origem = origem;
        this.destino = destino;
        this.partida = partida;
        this.chegada = chegada;
        this.companhia = companhia;
        this.aeronave = aeronave;
        this.duracao = duracao;
    }

    public String getNumero() {
        return numero;
    }

    public Aeroporto getOrigem() {
        return origem;
    }

    public Aeroporto getDestino() {
        return destino;
    }

    public LocalDateTime getPartida() {
        return partida;
    }

    public LocalDateTime getChegada() {
        return chegada;
    }

    public Companhia getCompanhia() {
        return companhia;
    }

    public Aeronave getAeronave() {
        return aeronave;
    }

    public long getDuracao() {
        return duracao;
    }

    @Override
    public String toString() {
        return "Voo{" +
                ", partida=" + partida +
                ", chegada=" + chegada +
                ", companhia=" + companhia +
                ", duracao=" + duracao +
                '}';
    }
}
