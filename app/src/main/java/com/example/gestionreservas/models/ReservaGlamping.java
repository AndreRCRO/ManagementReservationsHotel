package com.example.gestionreservas.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReservaGlamping extends Reserva implements Serializable {
    private int metrosCuadrados;
    private boolean tieneChimenea;
    private int capacidadMaxima;
    private String tipoExperiencia;
    private List<String> actividadesIncluidas;

    // Constructor vacío requerido para deserialización
    public ReservaGlamping() {
        super();
        this.actividadesIncluidas = new ArrayList<>();
    }

    // Constructor completo
    public ReservaGlamping(String codigo, String cliente, String fechaEntrada, String fechaSalida,
                           double precioTotal, String tipo, InfoAdicional informacionAdicional,
                           int metrosCuadrados, boolean tieneChimenea, int capacidadMaxima,
                           String tipoExperiencia, List<String> actividadesIncluidas) {
        super(codigo, cliente, fechaEntrada, fechaSalida, precioTotal, tipo, informacionAdicional);
        this.metrosCuadrados = metrosCuadrados;
        this.tieneChimenea = tieneChimenea;
        this.capacidadMaxima = capacidadMaxima;
        this.tipoExperiencia = tipoExperiencia;
        this.actividadesIncluidas = actividadesIncluidas != null ?
                new ArrayList<>(actividadesIncluidas) : new ArrayList<>();
    }

    // Getters y Setters
    public int getMetrosCuadrados() {
        return metrosCuadrados;
    }

    public void setMetrosCuadrados(int metrosCuadrados) {
        this.metrosCuadrados = metrosCuadrados;
    }

    public boolean isTieneChimenea() {
        return tieneChimenea;
    }

    public void setTieneChimenea(boolean tieneChimenea) {
        this.tieneChimenea = tieneChimenea;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public String getTipoExperiencia() {
        return tipoExperiencia;
    }

    public void setTipoExperiencia(String tipoExperiencia) {
        this.tipoExperiencia = tipoExperiencia;
    }

    public List<String> getActividadesIncluidas() {
        return actividadesIncluidas;
    }

    public void setActividadesIncluidas(List<String> actividadesIncluidas) {
        this.actividadesIncluidas = actividadesIncluidas != null ?
                new ArrayList<>(actividadesIncluidas) : new ArrayList<>();
    }

    @Override
    public Reserva clonar() {
        return new ReservaGlamping(
                getCodigo(),
                getCliente(),
                getFechaEntrada(),
                getFechaSalida(),
                getPrecioTotal(),
                getTipo(),
                getInformacionAdicional(),
                this.metrosCuadrados,
                this.tieneChimenea,
                this.capacidadMaxima,
                this.tipoExperiencia,
                this.actividadesIncluidas
        );
    }
}
