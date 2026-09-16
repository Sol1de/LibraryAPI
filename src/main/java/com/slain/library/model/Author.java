package com.slain.library.model;

import com.slain.library.enums.GenderType;
import jakarta.persistence.*;

import java.util.UUID;

public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String firstName;

    private String lastName;

    @Enumerated(value = EnumType.STRING)
    private GenderType gender;

}
