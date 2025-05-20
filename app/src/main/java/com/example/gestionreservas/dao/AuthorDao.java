package com.example.gestionreservas.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.gestionreservas.entity.Author;

import java.util.List;

@Dao
public interface AuthorDao {
    @Query("SELECT * FROM authors")
    List<Author> getAllAuthors();

    @Query("SELECT * FROM authors WHERE idAuthor = :id")
    Author getAuthorById(int id);

    @Insert
    long insertAuthor(Author author);

    @Update
    void updateAuthor(Author author);

    @Delete
    void deleteAuthor(Author author);
}
