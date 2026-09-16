package com.slain.library.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
public class BookShelf {

     @Id
     @GeneratedValue(strategy = GenerationType.UUID)
     private UUID id;

     @OneToMany(mappedBy = "bookShelf")
     private List<Shelf> shelf;

}
