package com.example.gestionreservas.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

public class BookWithAuthor {
    @Embedded
    public Book book;

    @Relation(
            parentColumn = "idAuthor",
            entityColumn = "idAuthor"
    )
    public Author author;
}
