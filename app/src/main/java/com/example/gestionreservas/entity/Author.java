package com.example.gestionreservas.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "authors")
public class Author {
    @PrimaryKey(autoGenerate = true)
    private int idAuthor;

    private String name;
    private String nationality;
    private String birthDate;
    private String email;

    // Getters and Setters
    public int getIdAuthor() { return idAuthor; }
    public void setIdAuthor(int idAuthor) { this.idAuthor = idAuthor; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }

    public String getBirthDate() { return birthDate; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
