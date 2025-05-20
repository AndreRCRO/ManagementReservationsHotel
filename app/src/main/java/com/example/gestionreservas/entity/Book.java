package com.example.gestionreservas.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(
        tableName = "books",
        foreignKeys = {
                @ForeignKey(
                        entity = Author.class,
                        parentColumns = "idAuthor",
                        childColumns = "idAuthor",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Publisher.class,
                        parentColumns = "idPublisher",
                        childColumns = "idPublisher",
                        onDelete = ForeignKey.CASCADE
                )
        }
)
public class Book implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int idBook;

    private String title;
    private String isbn;
    private int publicationYear;
    private int idAuthor; // Foreign Key
    private int idPublisher; // Foreign Key

    // Getters and Setters
    public int getIdBook() { return idBook; }
    public void setIdBook(int idBook) { this.idBook = idBook; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public int getPublicationYear() { return publicationYear; }
    public void setPublicationYear(int publicationYear) { this.publicationYear = publicationYear; }

    public int getIdAuthor() { return idAuthor; }
    public void setIdAuthor(int idAuthor) { this.idAuthor = idAuthor; }

    public int getIdPublisher() { return idPublisher; }
    public void setIdPublisher(int idPublisher) { this.idPublisher = idPublisher; }
}
