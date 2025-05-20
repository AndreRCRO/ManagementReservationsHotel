package com.example.gestionreservas.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.gestionreservas.dao.AuthorDao;
import com.example.gestionreservas.dao.BookDao;
import com.example.gestionreservas.dao.PublisherDao;
import com.example.gestionreservas.entity.Author;
import com.example.gestionreservas.entity.Book;
import com.example.gestionreservas.entity.Publisher;

@Database(entities = {Book.class, Author.class, Publisher.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "library_db";
    private static AppDatabase instance;

    public abstract BookDao bookDao();
    public abstract AuthorDao authorDao();
    public abstract PublisherDao publisherDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    DATABASE_NAME
            ).fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
