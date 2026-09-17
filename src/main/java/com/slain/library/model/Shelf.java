package com.slain.library.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class Shelf {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToMany(mappedBy = "shelf")
    private List<Book> book;

    @ManyToOne
    private BookShelf bookShelf;

    // Getters
    public UUID getId() {
        return id;
    }

    public List<Book> getBook() {
        return book;
    }

    public BookShelf getBookShelf() {
        return bookShelf;
    }

    // Setters
    public void setBook(List<Book> book) {
        this.book = book;
    }

    public void setBookShelf(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
    }
}
