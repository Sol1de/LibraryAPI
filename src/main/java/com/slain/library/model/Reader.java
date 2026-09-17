package com.slain.library.model;

import com.slain.library.enums.GenderType;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String firstName;

    private String lastName;

    @Enumerated(value =  EnumType.STRING)
    private GenderType gender;

    @OneToMany(mappedBy = "reader")
    private List<Book> books;

    // Getters
    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public GenderType getGender() {
        return gender;
    }

    public List<Book> getBooks() {
        return books;
    }

    // Setters
    public void setId(UUID id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
