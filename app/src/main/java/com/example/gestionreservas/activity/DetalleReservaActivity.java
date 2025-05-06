package com.example.gestionreservas.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gestionreservas.R;
import com.example.gestionreservas.models.Reserva;

public class DetalleReservaActivity extends AppCompatActivity {

    private TextView txtCodigo, txtCliente, txtFechas, txtPrecio, txtTipo, txtInfoAdicional;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle_reserva);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar vistas
        txtCodigo = findViewById(R.id.txtCodigo);
        txtCliente = findViewById(R.id.txtCliente);
        txtFechas = findViewById(R.id.txtFechas);
        txtPrecio = findViewById(R.id.txtPrecio);
        txtTipo = findViewById(R.id.txtTipo);
        txtInfoAdicional = findViewById(R.id.txtInfoAdicional);

        Reserva reserva = (Reserva) getIntent().getSerializableExtra("reserva");
        if (reserva != null) {
            mostrarDetallesReserva(reserva);
        }
    }

    private void mostrarDetallesReserva(Reserva reserva) {
        txtCodigo.setText(reserva.getCodigo());
        txtCliente.setText(reserva.getCliente());
        txtFechas.setText(String.format("%s al %s", reserva.getFechaEntrada(), reserva.getFechaSalida()));
        txtPrecio.setText(String.format("$%.2f", reserva.getPrecioTotal()));
        txtTipo.setText(reserva.getTipo());
        txtInfoAdicional.setText(reserva.getInformacionAdicional() != null
                ? reserva.getInformacionAdicional().toString()
                : "Sin información adicional");
    }
}