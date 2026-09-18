package com.slain.library.model;

import com.slain.library.enums.BookStatus;
import com.slain.library.enums.BookType;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.UUID;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;

    @Enumerated(EnumType.STRING)
    private BookType type;

    @Enumerated(EnumType.STRING)
    private BookStatus status;

    @ManyToOne
    private Shelf shelf;

    @JsonIgnore
    @ManyToOne(optional = true)
    private Reader reader;

    @ManyToOne
    private Author author;

    // Getters
    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public BookType getType() {
        return type;
    }

    public BookStatus getStatus() {
        return status;
    }

    public Reader getReader() {
        return reader;
    }

    public Shelf getShelf() {
        return shelf;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setType(BookType type) {
        this.type = type;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public void setShelf(Shelf shelf) {
        this.shelf = shelf;
    }
}
