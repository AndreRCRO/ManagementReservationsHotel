package com.example.gestionreservas.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.gestionreservas.models.Reserva;
import com.example.gestionreservas.models.InfoAdicional;
import com.example.gestionreservas.models.Reserva;
import com.example.gestionreservas.models.ReservaCabana;
import com.example.gestionreservas.models.ReservaGlamping;
import com.example.gestionreservas.models.ReservaHotel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ApiService {
    private static final String TAG = "ApiService";
    private static final String API_URL = "https://raw.githubusercontent.com/adancondori/TareaAPI/refs/heads/main/api/reservas.json";

    private static ApiService instance;
    private RequestQueue requestQueue;
    private final Context context;
    private final Gson gson;

    private ApiService(Context context) {
        this.context = context.getApplicationContext();
        this.requestQueue = getRequestQueue();
        this.gson = new Gson();
    }

    public static synchronized ApiService getInstance(Context context) {
        if (instance == null) {
            instance = new ApiService(context);
        }
        return instance;
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }

    // Interfaz para manejar la respuesta de la API
    public interface ReservasCallback {
        void onSuccess(List<Reserva> reservas);
        void onError(String error);
    }

    //Metodo para obtener reservas
    public void obtenerReservas(final ReservasCallback callback) {
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                API_URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            List<Reserva> reservas = parseReservas(response);
                            callback.onSuccess(reservas);
                        } catch (JSONException e) {
                            Log.e(TAG, "Error al parsear JSON", e);
                            callback.onError("Error al procesar los datos: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error en la solicitud", error);
                        callback.onError("Error en la solicitud: " + error.getMessage());
                    }
                });

        getRequestQueue().add(request);
    }

    //Metodo para parsear las reservas del Json
    private List<Reserva> parseReservas(JSONObject jsonObject) throws JSONException {
        List<Reserva> reservas = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("reservas");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonReserva = jsonArray.getJSONObject(i);
            String tipo = jsonReserva.getString("tipo");

            Reserva reserva;
            switch (tipo) {
                case "ReservaHotel":
                    reserva = gson.fromJson(jsonReserva.toString(), ReservaHotel.class);
                    break;
                case "ReservaCabana":
                    reserva = gson.fromJson(jsonReserva.toString(), ReservaCabana.class);
                    break;
                case "ReservaGlamping":
                    reserva = gson.fromJson(jsonReserva.toString(), ReservaGlamping.class);
                    break;
                default:
                    reserva = gson.fromJson(jsonReserva.toString(), Reserva.class);
                    break;
            }
            reservas.add(reserva);
        }

        return reservas;
    }
}
