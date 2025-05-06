package com.example.gestionreservas.repository;

import android.content.Context;

import com.example.gestionreservas.api.ApiService;
import com.example.gestionreservas.models.Reserva;

import java.util.ArrayList;
import java.util.List;

public class ReservaRepository {
    private static ReservaRepository instance;
    private final ApiService apiService;
    private List<Reserva> reservasList;
    private List<RepositoryCallback> callbacks;

    // Constructor privado para el patrón Singleton
    private ReservaRepository(Context context) {
        this.apiService = ApiService.getInstance(context);
        this.reservasList = new ArrayList<>();
        this.callbacks = new ArrayList<>();
    }

    // Obtener instancia del repositorio
    public static synchronized ReservaRepository getInstance(Context context) {
        if (instance == null) {
            instance = new ReservaRepository(context);
        }
        return instance;
    }

    // Interfaz para notificaciones de cambios en los datos
    public interface RepositoryCallback {
        void onDataChanged();
    }

    // Registrar un callback
    public void registerCallback(RepositoryCallback callback) {
        if (!callbacks.contains(callback)) {
            callbacks.add(callback);
        }
    }

    // Anular registro de un callback
    public void unregisterCallback(RepositoryCallback callback) {
        callbacks.remove(callback);
    }

    // Notificar a todos los callbacks
    private void notifyDataChanged() {
        for (RepositoryCallback callback : callbacks) {
            callback.onDataChanged();
        }
    }

    // Cargar datos desde la API
    public void cargarDatos(final RepositoryCallback callback) {
        apiService.obtenerReservas(new ApiService.ReservasCallback() {
            @Override
            public void onSuccess(List<Reserva> reservas) {
                reservasList.clear();
                reservasList.addAll(reservas);
                notifyDataChanged();
                if (callback != null) {
                    callback.onDataChanged();
                }
            }

            @Override
            public void onError(String error) {
                // Manejar error
            }
        });
    }

    // Obtener todas las reservas
    public List<Reserva> obtenerReservas() {
        return new ArrayList<>(reservasList);
    }

    // Obtener reserva por código
    public Reserva obtenerReservaPorCodigo(String codigo) {
        for (Reserva reserva : reservasList) {
            if (reserva.getCodigo().equals(codigo)) {
                return reserva;
            }
        }
        return null;
    }

    // Agregar nueva reserva
    public void agregarReserva(Reserva reserva) {
        // Generar un nuevo código
        String nuevoCodigo = generarNuevoCodigo(reserva.getTipo());
        reserva.setCodigo(nuevoCodigo);

        reservasList.add(reserva);
        notifyDataChanged();
    }

    // Actualizar reserva existente
    public void actualizarReserva(Reserva reservaActualizada) {
        for (int i = 0; i < reservasList.size(); i++) {
            if (reservasList.get(i).getCodigo().equals(reservaActualizada.getCodigo())) {
                reservasList.set(i, reservaActualizada);
                notifyDataChanged();
                return;
            }
        }
    }

    // Eliminar reserva
    public void eliminarReserva(String codigo) {
        for (int i = 0; i < reservasList.size(); i++) {
            if (reservasList.get(i).getCodigo().equals(codigo)) {
                reservasList.remove(i);
                notifyDataChanged();
                return;
            }
        }
    }

    // Generar un nuevo código para una reserva
    private String generarNuevoCodigo(String tipo) {
        // Determinar el prefijo según el tipo
        String prefijo;
        switch (tipo) {
            case "ReservaHotel":
                prefijo = "H";
                break;
            case "ReservaCabana":
                prefijo = "C";
                break;
            case "ReservaGlamping":
                prefijo = "G";
                break;
            default:
                prefijo = "R";
                break;
        }

        // Encontrar el mayor número existente para este tipo
        int maxNum = 0;
        for (Reserva reserva : reservasList) {
            if (reserva.getCodigo().startsWith(prefijo)) {
                try {
                    int num = Integer.parseInt(reserva.getCodigo().substring(1));
                    maxNum = Math.max(maxNum, num);
                } catch (NumberFormatException e) {
                    // Ignorar códigos con formato no válido
                }
            }
        }

        // Generar nuevo código (prefijo + número incrementado)
        return String.format("%s%03d", prefijo, maxNum + 1);
    }
}
