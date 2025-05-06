package com.example.gestionreservas.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InfoAdicional implements Serializable {
    private int esperanzaVida;
    private List<DatoAdicional> datos;

    // Constructor vacío
    public InfoAdicional() {
        this.datos = new ArrayList<>();
    }

    // Constructor completo
    public InfoAdicional(int esperanzaVida, List<DatoAdicional> datos) {
        this.esperanzaVida = esperanzaVida;
        this.datos = datos != null ? new ArrayList<>(datos) : new ArrayList<>();
    }

    // Getters y Setters
    public int getEsperanzaVida() {
        return esperanzaVida;
    }

    public void setEsperanzaVida(int esperanzaVida) {
        this.esperanzaVida = esperanzaVida;
    }

    public List<DatoAdicional> getDatos() {
        return datos;
    }

    public void setDatos(List<DatoAdicional> datos) {
        this.datos = datos != null ? new ArrayList<>(datos) : new ArrayList<>();
    }

    // Clase interna para representar cada dato adicional
    public static class DatoAdicional implements Serializable {
        private String nombreDato;
        private String valor;

        // Constructor vacío
        public DatoAdicional() {
        }

        // Constructor completo
        public DatoAdicional(String nombreDato, String valor) {
            this.nombreDato = nombreDato;
            this.valor = valor;
        }

        // Getters y Setters
        public String getNombreDato() {
            return nombreDato;
        }

        public void setNombreDato(String nombreDato) {
            this.nombreDato = nombreDato;
        }

        public String getValor() {
            return valor;
        }

        public void setValor(String valor) {
            this.valor = valor;
        }
    }
}
