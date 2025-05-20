package com.example.gestionreservas.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionreservas.R;
import com.example.gestionreservas.adapter.LibroAdapter;
import com.example.gestionreservas.database.AppDatabase;
import com.example.gestionreservas.entity.Book;
import com.example.gestionreservas.entity.BookWithAuthor;

import java.util.List;

public class ListadoLibrosActivity extends AppCompatActivity {

    private RecyclerView recyclerViewLibros;
    private LibroAdapter libroAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_libros);

        recyclerViewLibros = findViewById(R.id.recyclerViewLibros);
        recyclerViewLibros.setLayoutManager(new LinearLayoutManager(this));

        cargarLibros();
    }

    private void cargarLibros() {
        AppDatabase db = AppDatabase.getInstance(this);
        new Thread(() -> {
            List<BookWithAuthor> libros = db.bookDao().getBooksWithAuthors();
            runOnUiThread(() -> {
                if (libroAdapter == null) {
                    libroAdapter = new LibroAdapter(libros, this::editarLibro, this::eliminarLibro);
                    recyclerViewLibros.setAdapter(libroAdapter);
                } else {
                    libroAdapter.notifyDataSetChanged();
                }
            });
        }).start();
    }

    private void editarLibro(BookWithAuthor libro) {
        Intent intent = new Intent(this, EditarLibroActivity.class);
        intent.putExtra("libro", libro.book);
        startActivityForResult(intent, 1); // Usar startActivityForResult
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Book libroActualizado = (Book) data.getSerializableExtra("libroActualizado");
            if (libroActualizado != null) {
                actualizarLibroEnLista(libroActualizado);
            }
        }
    }

    private void actualizarLibroEnLista(Book libroActualizado) {
        AppDatabase db = AppDatabase.getInstance(this);
        new Thread(() -> {
            BookWithAuthor libroConAutorActualizado = db.bookDao().getBookWithAuthorById(libroActualizado.getIdBook());
            runOnUiThread(() -> {
                for (int i = 0; i < libroAdapter.getItemCount(); i++) {
                    BookWithAuthor libroConAutor = libroAdapter.getLibros().get(i);
                    if (libroConAutor.book.getIdBook() == libroActualizado.getIdBook()) {
                        libroAdapter.getLibros().set(i, libroConAutorActualizado);
                        libroAdapter.notifyItemChanged(i);
                        break;
                    }
                }
            });
        }).start();
    }

    private void eliminarLibro(BookWithAuthor libro) {
        AppDatabase db = AppDatabase.getInstance(this);
        new Thread(() -> {
            db.bookDao().deleteBook(libro.book);
            runOnUiThread(() -> libroAdapter.removeLibro(libro));
        }).start();
    }

}