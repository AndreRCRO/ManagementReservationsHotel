package com.example.gestionreservas.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Reserva implements Serializable {
    private String codigo;
    private String cliente;
    private String fechaEntrada;
    private String fechaSalida;
    private double precioTotal;
    private String tipo;
    private InfoAdicional informacionAdicional;


    public Reserva(String codigo, String cliente, String fechaEntrada, String fechaSalida,
                   double precioTotal, String tipo, InfoAdicional informacionAdicional) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.precioTotal = precioTotal;
        this.tipo = tipo;
        this.informacionAdicional = informacionAdicional;
    }

    public Reserva() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(String fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public InfoAdicional getInformacionAdicional() {
        return informacionAdicional;
    }

    public void setInformacionAdicional(InfoAdicional informacionAdicional) {
        this.informacionAdicional = informacionAdicional;
    }

    // Metodo para calcular la estadia
    public int getDiasEstadia() {
        try {
            return informacionAdicional.getEsperanzaVida();
        } catch (Exception e) {
            return 0;
        }
    }

    // Metodo para clonar la reserva
    public Reserva clonar() {
        return new Reserva(
                this.codigo,
                this.cliente,
                this.fechaEntrada,
                this.fechaSalida,
                this.precioTotal,
                this.tipo,
                this.informacionAdicional
        );
    }

}

