package com.example.gestionreservas.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "publishers")
public class Publisher {
    @PrimaryKey(autoGenerate = true)
    private int idPublisher;

    private String name;
    private String country;
    private String contact;
    private String address;

    // Getters and Setters
    public int getIdPublisher() { return idPublisher; }
    public void setIdPublisher(int idPublisher) { this.idPublisher = idPublisher; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
