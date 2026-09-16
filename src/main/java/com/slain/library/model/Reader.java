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
}
