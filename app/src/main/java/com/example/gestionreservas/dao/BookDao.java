package com.example.gestionreservas.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;


import com.example.gestionreservas.entity.Book;
import com.example.gestionreservas.entity.BookWithAuthor;

import java.util.List;

@Dao
public interface BookDao {
    @Query("SELECT * FROM books")
    List<Book> getAllBooks();

    @Query("SELECT * FROM books WHERE idBook = :id")
    Book getBookById(int id);

    @Transaction
    @Query("SELECT * FROM books")
    List<BookWithAuthor> getBooksWithAuthors();

    @Transaction
    @Query("SELECT * FROM books WHERE idBook = :id")
    BookWithAuthor getBookWithAuthorById(int id);

    @Insert
    long insertBook(Book book);

    @Update
    void updateBook(Book book);

    @Delete
    void deleteBook(Book book);
}
