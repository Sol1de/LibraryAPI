package com.slain.library.model;

import com.slain.library.enums.BookStatus;
import com.slain.library.enums.BookType;
import jakarta.persistence.*;

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

    @ManyToOne(optional = true)
    private Reader reader;

    @ManyToOne
    private Author author;

    // Methods
    public void update(Book book) {
        this.title = book.getTitle();
        this.type = book.getType();
        this.status = book.getStatus();
        this.author = book.getAuthor();
    }

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
}
