package com.example.gestionreservas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionreservas.R;
import com.example.gestionreservas.entity.Book;
import com.example.gestionreservas.entity.BookWithAuthor;

import java.util.List;

public class LibroAdapter extends RecyclerView.Adapter<LibroAdapter.LibroViewHolder> {

    private final List<BookWithAuthor> libros;
    private final OnLibroClickListener editarListener;
    private final OnLibroClickListener eliminarListener;

    public interface OnLibroClickListener {
        void onClick(BookWithAuthor libro);
    }

    public List<BookWithAuthor> getLibros() {
        return libros;
    }

    public LibroAdapter(List<BookWithAuthor> libros, OnLibroClickListener editarListener, OnLibroClickListener eliminarListener) {
        this.libros = libros;
        this.editarListener = editarListener;
        this.eliminarListener = eliminarListener;
    }

    @NonNull
    @Override
    public LibroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_libro, parent, false);
        return new LibroViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LibroViewHolder holder, int position) {
        BookWithAuthor libro = libros.get(position);
        holder.txtTitulo.setText(libro.book.getTitle());
        holder.txtAutor.setText(libro.author != null ? "Autor: " + libro.author.getName() : "Autor desconocido");
        holder.txtIsbn.setText("ISBN: " + libro.book.getIsbn());

        holder.btnEditar.setOnClickListener(v -> editarListener.onClick(libro));
        holder.btnEliminar.setOnClickListener(v -> eliminarListener.onClick(libro));
    }

    @Override
    public int getItemCount() {
        return libros.size();
    }

    public void removeLibro(BookWithAuthor libro) {
        int position = libros.indexOf(libro);
        if (position != -1) {
            libros.remove(position);
            notifyItemRemoved(position);
        }
    }

    static class LibroViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitulo, txtAutor, txtIsbn;
        View btnEditar, btnEliminar;

        public LibroViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            txtAutor = itemView.findViewById(R.id.txtAutor);
            txtIsbn = itemView.findViewById(R.id.txtIsbn); // Initialize txtIsbn
            btnEditar = itemView.findViewById(R.id.btnEditar); // Initialize btnEditar
            btnEliminar = itemView.findViewById(R.id.btnEliminar); // Initialize btnEliminar
        }
    }
}
