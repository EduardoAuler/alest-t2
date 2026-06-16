package model;

public class Companhia {
    private String codigo;
    private String nome;
    private String pais;

    public Companhia(String codiggo, String nome, String pais) {
        this.codigo = codiggo;
        this.nome = nome;
        this.pais = pais;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getPais() {
        return pais;
    }
}
