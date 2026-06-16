package model;

public class Aeronave {
    private String codigo;
    private String modelo;

    public Aeronave(String codigo, String modelo) {
        this.codigo = codigo;
        this.modelo = modelo;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getModelo() {
        return modelo;
    }
}
