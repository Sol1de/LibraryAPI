package com.slain.library.seeder;

import com.slain.library.model.Library;
import com.slain.library.repository.LibraryRepository;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class LibrarySeeder {
    private final LibraryRepository repository;

    public LibrarySeeder(LibraryRepository repository) {
        this.repository = repository;
    }

    public List<Library> getAll() {
        return repository.findAll();
    }

    public boolean isEmpty() {
        return repository.count() == 0;
    }

    public Library seed() {
        Library library = new Library();
        library.setName("Bibliothèque de démonstration");
        library.setCountry("France");
        library.setCity("Paris");
        library.setAddress("10 rue des Livres");
        return repository.save(library);
    }
}
