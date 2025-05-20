package com.example.gestionreservas.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gestionreservas.R;
import com.example.gestionreservas.database.AppDatabase;
import com.example.gestionreservas.entity.Author;
import com.example.gestionreservas.entity.Book;
import com.example.gestionreservas.entity.Publisher;

public class FormularioLibroActivity extends AppCompatActivity {

    private EditText edtTitulo, edtIsbn, edtAnioPublicacion, edtAutor, edtEditorial;
    private Button btnGuardar, btnCancelar;

    private AppDatabase db;
    private Book libro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_libro);

        db = AppDatabase.getInstance(this);

        edtTitulo = findViewById(R.id.edtTitulo);
        edtIsbn = findViewById(R.id.edtIsbn);
        edtAnioPublicacion = findViewById(R.id.edtAnioPublicacion);
        edtAutor = findViewById(R.id.edtAutor);
        edtEditorial = findViewById(R.id.edtEditorial);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);

        // Check if editing an existing book
        libro = (Book) getIntent().getSerializableExtra("libro");
        if (libro != null) {
            loadBookData();
        }

        btnGuardar.setOnClickListener(v -> saveBook());
        btnCancelar.setOnClickListener(v -> finish());
    }

    private void loadBookData() {
        edtTitulo.setText(libro.getTitle());
        edtIsbn.setText(libro.getIsbn());
        edtAnioPublicacion.setText(String.valueOf(libro.getPublicationYear()));

        Author author = db.authorDao().getAuthorById(libro.getIdAuthor());
        if (author != null) {
            edtAutor.setText(author.getName());
        }

        Publisher publisher = db.publisherDao().getPublisherById(libro.getIdPublisher());
        if (publisher != null) {
            edtEditorial.setText(publisher.getName());
        }
    }

    private void saveBook() {
        String titulo = edtTitulo.getText().toString().trim();
        String isbn = edtIsbn.getText().toString().trim();
        String anioPublicacionStr = edtAnioPublicacion.getText().toString().trim();
        String autorNombre = edtAutor.getText().toString().trim();
        String editorialNombre = edtEditorial.getText().toString().trim();

        if (titulo.isEmpty() || titulo.length() < 3) {
            Toast.makeText(this, "El título es obligatorio y debe tener al menos 3 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isbn.isEmpty()) {
            Toast.makeText(this, "El ISBN es obligatorio", Toast.LENGTH_SHORT).show();
            return;
        }
        if (anioPublicacionStr.isEmpty()) {
            Toast.makeText(this, "El año de publicación es obligatorio", Toast.LENGTH_SHORT).show();
            return;
        }
        if (autorNombre.isEmpty()) {
            Toast.makeText(this, "El nombre del autor es obligatorio", Toast.LENGTH_SHORT).show();
            return;
        }
        if (editorialNombre.isEmpty()) {
            Toast.makeText(this, "El nombre de la editorial es obligatorio", Toast.LENGTH_SHORT).show();
            return;
        }

        int anioPublicacion = Integer.parseInt(anioPublicacionStr);

        new Thread(() -> {
            Author author = new Author();
            author.setName(autorNombre);
            long authorId = db.authorDao().insertAuthor(author);

            Publisher publisher = new Publisher();
            publisher.setName(editorialNombre);
            long publisherId = db.publisherDao().insertPublisher(publisher);

            if (libro == null) {
                libro = new Book();
            }

            libro.setTitle(titulo);
            libro.setIsbn(isbn);
            libro.setPublicationYear(anioPublicacion);
            libro.setIdAuthor((int) authorId);
            libro.setIdPublisher((int) publisherId);

            if (libro.getIdBook() == 0) {
                db.bookDao().insertBook(libro);
            } else {
                db.bookDao().updateBook(libro);
            }

            runOnUiThread(() -> {
                Toast.makeText(this, "Libro guardado correctamente", Toast.LENGTH_SHORT).show();
                finish();
            });
        }).start();
    }
}