package model;

import java.util.ArrayList;
import java.util.List;

public class Aeroporto {
    private String codigo;
    private String nome;
    private List<Voo> voosSaida;
    private int grauEntrada;

    public Aeroporto(String codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
        voosSaida = new ArrayList<>();
        grauEntrada = 0;
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

    public int getGrauEntrada() {
        return grauEntrada;
    }

    public int getGrauSaida(){
        return voosSaida.size();
    }

    public int getGrauTotal() {
        return grauEntrada + voosSaida.size();
    }

    public void setGrauEntrada(int grauEntrada) {
        this.grauEntrada = grauEntrada;
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
