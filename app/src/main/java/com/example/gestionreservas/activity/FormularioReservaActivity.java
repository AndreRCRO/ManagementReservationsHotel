package com.example.gestionreservas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gestionreservas.R;
import com.example.gestionreservas.models.Reserva;
import com.example.gestionreservas.repository.ReservaRepository;

public class FormularioReservaActivity extends AppCompatActivity {

    private EditText edtCliente, edtFechaEntrada, edtFechaSalida, edtPrecio, edtTipo;
    private Button btnGuardar;
    private ReservaRepository repository;
    private Reserva reserva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_formulario_reserva);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar vistas
        edtCliente = findViewById(R.id.edtCliente);
        edtFechaEntrada = findViewById(R.id.edtFechaEntrada);
        edtFechaSalida = findViewById(R.id.edtFechaSalida);
        edtPrecio = findViewById(R.id.edtPrecio);
        edtTipo = findViewById(R.id.edtTipo);
        btnGuardar = findViewById(R.id.btnGuardar);

        // Inicializar repositorio
        repository = ReservaRepository.getInstance(this);

        // Verificar si es edición
        reserva = (Reserva) getIntent().getSerializableExtra("reserva");
        if (reserva != null) {
            cargarDatosReserva(reserva);
        }

        // Configurar botón guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarReserva();
            }
        });
    }

    private void cargarDatosReserva(Reserva reserva) {
        edtCliente.setText(reserva.getCliente());
        edtFechaEntrada.setText(reserva.getFechaEntrada());
        edtFechaSalida.setText(reserva.getFechaSalida());
        edtPrecio.setText(String.valueOf(reserva.getPrecioTotal()));
        edtTipo.setText(reserva.getTipo());
    }

    private void guardarReserva() {
        String cliente = edtCliente.getText().toString();
        String fechaEntrada = edtFechaEntrada.getText().toString();
        String fechaSalida = edtFechaSalida.getText().toString();
        double precioTotal = Double.parseDouble(edtPrecio.getText().toString());
        String tipo = edtTipo.getText().toString();

        if (reserva == null) {
            // Crear nueva reserva
            reserva = new Reserva();
            reserva.setCliente(cliente);
            reserva.setFechaEntrada(fechaEntrada);
            reserva.setFechaSalida(fechaSalida);
            reserva.setPrecioTotal(precioTotal);
            reserva.setTipo(tipo);
            repository.agregarReserva(reserva);

            // Enviar resultado indicando que se agregó una nueva reserva
            Intent resultIntent = new Intent();
            resultIntent.putExtra("nuevaReserva", reserva);
            setResult(RESULT_OK, resultIntent);
        } else {
            // Actualizar reserva existente
            reserva.setCliente(cliente);
            reserva.setFechaEntrada(fechaEntrada);
            reserva.setFechaSalida(fechaSalida);
            reserva.setPrecioTotal(precioTotal);
            reserva.setTipo(tipo);
            repository.actualizarReserva(reserva);

            // Enviar resultado indicando que se editó una reserva
            Intent resultIntent = new Intent();
            resultIntent.putExtra("reservaEditada", reserva);
            setResult(RESULT_OK, resultIntent);
        }

        Toast.makeText(this, "Reserva guardada correctamente", Toast.LENGTH_SHORT).show();
        finish();
    }

}