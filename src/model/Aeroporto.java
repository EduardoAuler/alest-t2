package model;

import java.util.ArrayList;
import java.util.List;

public class Aeroporto {
    private String codigo;
    private String nome;
    private List<Voo> voosSaida;

    public Aeroporto(String codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
        voosSaida = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public List<Voo> getVoosSaida() {
        return voosSaida;
    }

    public void addVoo(Voo voo){
        voosSaida.add(voo);
    }

    @Override
    public String toString() {
        return "Aeroporto{" +
                "codigo='" + codigo + '\'' +
                ", nome='" + nome + '\'' +
                ", voosSaida=" + voosSaida +
                '}';
    }
}
