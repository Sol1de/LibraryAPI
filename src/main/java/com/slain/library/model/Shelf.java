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

}
