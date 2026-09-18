package com.slain.library.seeder;

import com.slain.library.model.Library;
import com.slain.library.repository.LibraryRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class LibrarySeeder {
    private final LibraryRepository repository;

    public LibrarySeeder(LibraryRepository repository) {
        this.repository = repository;
    }

    public Library seed() {
        var matches = repository.findAll().stream()
                .filter(candidate -> "Bibliothèque de démonstration".equals(candidate.getName()))
                .toList();
        if (matches.size() > 1) {
            throw new IllegalStateException("Ambiguous library fixture: Bibliothèque de démonstration");
        }
        var library = matches.isEmpty() ? new Library() : matches.getFirst();
        library.setName("Bibliothèque de démonstration");
        library.setCountry("France");
        library.setCity("Paris");
        library.setAddress("10 rue des Livres");
        return repository.save(library);
    }
}
