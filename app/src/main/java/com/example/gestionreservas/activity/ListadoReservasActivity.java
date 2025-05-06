package com.example.gestionreservas.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionreservas.R;
import com.example.gestionreservas.adapter.ReservaAdapter;
import com.example.gestionreservas.models.Reserva;
import com.example.gestionreservas.repository.ReservaRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListadoReservasActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReservaAdapter adapter;
    private ProgressBar progressBar;
    private TextView txtEmpty;
    private ReservaRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_reservas);

        // Inicializar vistas
        recyclerView = findViewById(R.id.recyclerReservas);
        progressBar = findViewById(R.id.progressBar);
        txtEmpty = findViewById(R.id.txtEmpty);
        FloatingActionButton fabAgregar = findViewById(R.id.fabAgregar);

        // Setear el recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReservaAdapter(this, new ReservaAdapter.OnReservaClickListener() {
            @Override
            public void onReservaClick(Reserva reserva) {
                // Handle item click
                Intent intent = new Intent(ListadoReservasActivity.this, DetalleReservaActivity.class);
                intent.putExtra("reserva", reserva);
                startActivity(intent);
            }

            @Override
            public void onEditClick(Reserva reserva) {
                // Handle edit click
                Intent intent = new Intent(ListadoReservasActivity.this, FormularioReservaActivity.class);
                intent.putExtra("reserva", reserva);
                startActivityForResult(intent, 1);
            }

            @Override
            public void onDeleteClick(Reserva reserva) {
                new AlertDialog.Builder(ListadoReservasActivity.this)
                        .setTitle("Confirmar eliminación")
                        .setMessage("¿Estás seguro de que deseas eliminar esta reserva?")
                        .setPositiveButton("Eliminar", (dialog, which) -> {
                            repository.eliminarReserva(reserva.getCodigo());
                            adapter.eliminarReserva(reserva.getCodigo()); // Actualiza el adaptador directamente
                            if (adapter.getItemCount() == 0) {
                                txtEmpty.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();
            }
        });
        recyclerView.setAdapter(adapter);

        // Setear el FloatingActionButton
        fabAgregar.setOnClickListener(v -> {
            Intent intent = new Intent(ListadoReservasActivity.this, FormularioReservaActivity.class);
            startActivityForResult(intent, 1);
        });

        // Inicializar repositorio
        repository = ReservaRepository.getInstance(this);

        // cargar la data
        loadReservas();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Verifica que la respuesta proviene de la actividad de formulario (requestCode == 1),
        // que el resultado fue exitoso (RESULT_OK), y que hay datos en el Intent.
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {

            // Intenta obtener una nueva reserva desde los datos recibidos.
            Reserva nuevaReserva = (Reserva) data.getSerializableExtra("nuevaReserva");
            if (nuevaReserva != null) {
                // Agrega la nueva reserva al adaptador para mostrarla en la lista.
                adapter.agregarReserva(nuevaReserva);

                // Oculta el mensaje de "sin elementos" y muestra el RecyclerView.
                txtEmpty.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                return;
            }

            // Si no es una nueva reserva, intenta obtener una reserva editada.
            Reserva reservaEditada = (Reserva) data.getSerializableExtra("reservaEditada");
            if (reservaEditada != null) {
                // Actualiza la reserva existente en el adaptador.
                adapter.actualizarReserva(reservaEditada);
            }
        }
    }


    private void loadReservas() {
        // Mostrar el ProgressBar mientras se cargan los datos
        progressBar.setVisibility(View.VISIBLE);

        // Ocultar el mensaje de "sin datos" y la lista mientras se carga
        txtEmpty.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

        // Llamar al repositorio para cargar los datos de forma asíncrona
        repository.cargarDatos(() -> {
            // Obtener la lista de reservas cargadas desde el repositorio
            List<Reserva> reservas = repository.obtenerReservas();

            // Ocultar el ProgressBar porque ya se terminó de cargar
            progressBar.setVisibility(View.GONE);

            // Verificar si la lista está vacía
            if (reservas.isEmpty()) {
                // Si no hay reservas, mostrar el texto de "sin datos"
                txtEmpty.setVisibility(View.VISIBLE);
            } else {
                // Si hay reservas, ocultar el mensaje vacío y mostrar la lista
                txtEmpty.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                // Pasar las reservas al adaptador para que se muestren en pantalla
                adapter.setReservas(reservas);
            }
        });
    }

}