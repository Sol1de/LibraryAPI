package com.slain.library.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String country;

    private String city;

    private String address;

    @OneToMany(mappedBy = "library", cascade = CascadeType.REMOVE)
    private List<BookShelf> bookShelf;

    // Getters
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getAddress() {
        return address;
    }

    public List<BookShelf> getBookShelf() {
        return bookShelf;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBookShelf(List<BookShelf> bookShelf) {
        this.bookShelf = bookShelf;
    }
}
