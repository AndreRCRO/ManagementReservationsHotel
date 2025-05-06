package com.example.gestionreservas.models;

import java.io.Serializable;
public class ReservaHotel extends Reserva implements Serializable {
    private String tipoHabitacion;
    private boolean incluyeDesayuno;
    private int numeroHuespedes;

    public ReservaHotel() {
        super();
    }

    public ReservaHotel(String codigo, String cliente, String fechaEntrada, String fechaSalida,
                        double precioTotal, String tipo, InfoAdicional informacionAdicional,
                        String tipoHabitacion, boolean incluyeDesayuno, int numeroHuespedes) {
        super(codigo, cliente, fechaEntrada, fechaSalida, precioTotal, tipo, informacionAdicional);
        this.tipoHabitacion = tipoHabitacion;
        this.incluyeDesayuno = incluyeDesayuno;
        this.numeroHuespedes = numeroHuespedes;
    }

    // Getters y Setters
    public String getTipoHabitacion() {
        return tipoHabitacion;
    }

    public void setTipoHabitacion(String tipoHabitacion) {
        this.tipoHabitacion = tipoHabitacion;
    }

    public boolean isIncluyeDesayuno() {
        return incluyeDesayuno;
    }

    public void setIncluyeDesayuno(boolean incluyeDesayuno) {
        this.incluyeDesayuno = incluyeDesayuno;
    }

    public int getNumeroHuespedes() {
        return numeroHuespedes;
    }

    public void setNumeroHuespedes(int numeroHuespedes) {
        this.numeroHuespedes = numeroHuespedes;
    }

    @Override
    public Reserva clonar() {
        return new ReservaHotel(
                getCodigo(),
                getCliente(),
                getFechaEntrada(),
                getFechaSalida(),
                getPrecioTotal(),
                getTipo(),
                getInformacionAdicional(),
                this.tipoHabitacion,
                this.incluyeDesayuno,
                this.numeroHuespedes
        );
    }

}
