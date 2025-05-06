package com.example.gestionreservas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionreservas.R;
import com.example.gestionreservas.models.Reserva;
import com.example.gestionreservas.models.ReservaCabana;
import com.example.gestionreservas.models.ReservaGlamping;
import com.example.gestionreservas.models.ReservaHotel;

import java.util.ArrayList;
import java.util.List;

public class ReservaAdapter extends RecyclerView.Adapter<ReservaAdapter.ReservaViewHolder> {

    private List<Reserva> reservaList;
    private Context context;
    private OnReservaClickListener onReservaClickListener;

    // Interface para manejar clics en los elementos
    public interface OnReservaClickListener {
        void onReservaClick(Reserva reserva);

        void onEditClick(Reserva reserva);

        void onDeleteClick(Reserva reserva);
    }

    public ReservaAdapter(Context context, OnReservaClickListener listener) {
        this.context = context;
        this.reservaList = new ArrayList<>();
        this.onReservaClickListener = listener;
    }

    @NonNull
    @Override
    public ReservaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reserva, parent, false);
        return new ReservaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservaViewHolder holder, int position) {
        Reserva reserva = reservaList.get(position);
        holder.bind(reserva);
    }

    @Override
    public int getItemCount() {
        return reservaList.size();
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservaList.clear();
        this.reservaList.addAll(reservas);
        notifyDataSetChanged();
    }

    public void agregarReserva(Reserva reserva) {
        this.reservaList.add(reserva);
        notifyItemInserted(reservaList.size() - 1);
    }

    public void actualizarReserva(Reserva reserva) {
        for (int i = 0; i < reservaList.size(); i++) {
            if (reservaList.get(i).getCodigo().equals(reserva.getCodigo())) {
                reservaList.set(i, reserva);
                notifyItemChanged(i);
                break;
            }
        }
    }

    public void eliminarReserva(String codigo) {
        for (int i = 0; i < reservaList.size(); i++) {
            if (reservaList.get(i).getCodigo().equals(codigo)) {
                reservaList.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }

    class ReservaViewHolder extends RecyclerView.ViewHolder {
        private TextView txtCodigo, txtCliente, txtFechas, txtPrecio, txtTipo;
        private ImageView imgTipo, imgEditar, imgEliminar;

        public ReservaViewHolder(@NonNull View itemView) {
            super(itemView);

            // Inicializar vistas
            txtCodigo = itemView.findViewById(R.id.txtCodigo);
            txtCliente = itemView.findViewById(R.id.txtCliente);
            txtFechas = itemView.findViewById(R.id.txtFechas);
            txtPrecio = itemView.findViewById(R.id.txtPrecio);
            txtTipo = itemView.findViewById(R.id.txtTipo);
            imgTipo = itemView.findViewById(R.id.imgTipo);
            imgEditar = itemView.findViewById(R.id.imgEditar);
            imgEliminar = itemView.findViewById(R.id.imgEliminar);

            // Click en la vista completa
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onReservaClickListener != null) {
                        onReservaClickListener.onReservaClick(reservaList.get(position));
                    }
                }
            });

            // Click en editar
            imgEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onReservaClickListener != null) {
                        onReservaClickListener.onEditClick(reservaList.get(position));
                    }
                }
            });

            // Click en eliminar
            imgEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && onReservaClickListener != null) {
                        onReservaClickListener.onDeleteClick(reservaList.get(position));
                    }
                }
            });
        }

        public void bind(Reserva reserva) {
            txtCodigo.setText(reserva.getCodigo());
            txtCliente.setText(reserva.getCliente());
            txtFechas.setText(String.format("%s al %s", reserva.getFechaEntrada(), reserva.getFechaSalida()));
            txtPrecio.setText(String.format("$%.2f", reserva.getPrecioTotal()));

            // Configurar según el tipo de reserva
            switch (reserva.getTipo()) {
                case "ReservaHotel":
                    ReservaHotel hotel = (ReservaHotel) reserva;
                    txtTipo.setText(String.format("Hotel - %s", hotel.getTipoHabitacion()));
                    imgTipo.setImageResource(R.drawable.ic_hotel);
                    break;
                case "ReservaCabana":
                    ReservaCabana cabana = (ReservaCabana) reserva;
                    txtTipo.setText(String.format("Cabaña - %dm²", cabana.getMetrosCuadrados()));
                    imgTipo.setImageResource(R.drawable.ic_cabana);
                    break;
                case "ReservaGlamping":
                    ReservaGlamping glamping = (ReservaGlamping) reserva;
                    txtTipo.setText(String.format("Glamping - %s", glamping.getTipoExperiencia()));
                    imgTipo.setImageResource(R.drawable.ic_glamping);
                    break;
                default:
                    txtTipo.setText("Reserva");
                    imgTipo.setImageResource(R.drawable.ic_reserva);
                    break;
            }
        }
    }
}