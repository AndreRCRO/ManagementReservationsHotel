package com.example.gestionreservas.models;

import java.io.Serializable;

public class ReservaCabana extends Reserva implements Serializable {
    private int metrosCuadrados;
    private boolean tieneChimenea;
    private int capacidadMaxima;

    // Constructor vacío requerido para deserialización
    public ReservaCabana() {
        super();
    }

    // Constructor completo
    public ReservaCabana(String codigo, String cliente, String fechaEntrada, String fechaSalida,
                         double precioTotal, String tipo, InfoAdicional informacionAdicional,
                         int metrosCuadrados, boolean tieneChimenea, int capacidadMaxima) {
        super(codigo, cliente, fechaEntrada, fechaSalida, precioTotal, tipo, informacionAdicional);
        this.metrosCuadrados = metrosCuadrados;
        this.tieneChimenea = tieneChimenea;
        this.capacidadMaxima = capacidadMaxima;
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

    @Override
    public Reserva clonar() {
        return new ReservaCabana(
                getCodigo(),
                getCliente(),
                getFechaEntrada(),
                getFechaSalida(),
                getPrecioTotal(),
                getTipo(),
                getInformacionAdicional(),
                this.metrosCuadrados,
                this.tieneChimenea,
                this.capacidadMaxima
        );
    }
}
