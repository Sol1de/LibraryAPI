package com.slain.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryApiApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(LibraryApiApplication.class, args);
        if (java.util.Arrays.asList(args).contains("--library.command=true")) {
            context.close();
        }
    }

}
