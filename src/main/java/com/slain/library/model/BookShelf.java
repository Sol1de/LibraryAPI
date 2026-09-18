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

     // Getters
     public UUID getId() {
          return id;
     }

     public List<Shelf> getShelf() {
          return shelf;
     }

     // Setters
     public void setShelf(List<Shelf> shelf) {
          this.shelf = shelf;
     }
}
