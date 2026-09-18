package com.slain.library.model;

import com.slain.library.enums.GenderType;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.UUID;

@Entity
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    private String password;

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    private String firstName;

    private String lastName;

    @Enumerated(value =  EnumType.STRING)
    private GenderType gender;

    @JsonIgnore
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
