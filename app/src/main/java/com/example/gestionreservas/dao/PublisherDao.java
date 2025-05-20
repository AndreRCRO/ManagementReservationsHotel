package com.example.gestionreservas.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.gestionreservas.entity.Publisher;

import java.util.List;

@Dao
public interface PublisherDao {
    @Query("SELECT * FROM publishers")
    List<Publisher> getAllPublishers();

    @Query("SELECT * FROM publishers WHERE idPublisher = :id")
    Publisher getPublisherById(int id);

    @Insert
    long insertPublisher(Publisher publisher);

    @Update
    void updatePublisher(Publisher publisher);

    @Delete
    void deletePublisher(Publisher publisher);
}
