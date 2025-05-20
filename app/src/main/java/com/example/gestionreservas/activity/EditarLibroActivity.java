package com.example.gestionreservas.activity;

import android.content.Intent;
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

public class EditarLibroActivity extends AppCompatActivity {

    private EditText edtTitulo, edtIsbn, edtAnioPublicacion, edtAutor, edtEditorial;
    private Button btnGuardar, btnCancelar;

    private AppDatabase db;
    private Book libro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_libro);

        db = AppDatabase.getInstance(this);

        edtTitulo = findViewById(R.id.edtTitulo);
        edtIsbn = findViewById(R.id.edtIsbn);
        edtAnioPublicacion = findViewById(R.id.edtAnioPublicacion);
        edtAutor = findViewById(R.id.edtAutor);
        edtEditorial = findViewById(R.id.edtEditorial);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);

        libro = (Book) getIntent().getSerializableExtra("libro");
        if (libro != null) {
            loadBookData();
        } else {
            Toast.makeText(this, "Error al cargar el libro", Toast.LENGTH_SHORT).show();
            finish();
        }

        btnGuardar.setOnClickListener(v -> saveBook());
        btnCancelar.setOnClickListener(v -> finish());
    }

    private void loadBookData() {
        new Thread(() -> {
            Author author = db.authorDao().getAuthorById(libro.getIdAuthor());
            Publisher publisher = db.publisherDao().getPublisherById(libro.getIdPublisher());

            runOnUiThread(() -> {
                edtTitulo.setText(libro.getTitle());
                edtIsbn.setText(libro.getIsbn());
                edtAnioPublicacion.setText(String.valueOf(libro.getPublicationYear()));

                if (author != null) {
                    edtAutor.setText(author.getName());
                } else {
                    edtAutor.setText(""); // Maneja el caso de autor nulo
                }

                if (publisher != null) {
                    edtEditorial.setText(publisher.getName());
                } else {
                    edtEditorial.setText(""); // Maneja el caso de editorial nula
                }
            });
        }).start();
    }

    private void saveBook() {
        String titulo = edtTitulo.getText().toString().trim();
        String isbn = edtIsbn.getText().toString().trim();
        String anioPublicacionStr = edtAnioPublicacion.getText().toString().trim();
        String autorNombre = edtAutor.getText().toString().trim();
        String editorialNombre = edtEditorial.getText().toString().trim();

        if (titulo.isEmpty() || isbn.isEmpty() || anioPublicacionStr.isEmpty() || autorNombre.isEmpty() || editorialNombre.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
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

            libro.setTitle(titulo);
            libro.setIsbn(isbn);
            libro.setPublicationYear(anioPublicacion);
            libro.setIdAuthor((int) authorId);
            libro.setIdPublisher((int) publisherId);

            db.bookDao().updateBook(libro);

            runOnUiThread(() -> {
                Toast.makeText(this, "Libro actualizado correctamente", Toast.LENGTH_SHORT).show();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("libroActualizado", libro);
                setResult(RESULT_OK, resultIntent);
                finish();
            });
        }).start();
    }

}