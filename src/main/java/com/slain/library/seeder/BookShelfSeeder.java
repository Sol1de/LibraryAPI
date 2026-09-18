package com.slain.library.seeder;

import com.slain.library.model.BookShelf;
import com.slain.library.repository.BookShelfRepository;
import com.slain.library.model.Library;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class BookShelfSeeder {
    private final BookShelfRepository repository;

    public BookShelfSeeder(BookShelfRepository repository) {
        this.repository = repository;
    }

    public List<BookShelf> seed(Library library) {
        var fixtureCandidates = repository.findAll().stream()
                .filter(candidate -> candidate.getLibrary() != null
                        && Objects.equals(candidate.getLibrary().getId(), library.getId()))
                .sorted(Comparator.comparing(candidate -> candidate.getId().toString()))
                .toList();
        if (fixtureCandidates.size() > 2) {
            throw new IllegalStateException(
                    "Ambiguous bookshelf fixtures for library: " + library.getName());
        }
        var fixtures = new ArrayList<>(fixtureCandidates);
        while (fixtures.size() < 2) {
            var bookShelf = new BookShelf();
            bookShelf.setLibrary(library);
            fixtures.add(bookShelf);
        }
        return repository.saveAll(fixtures).stream()
                .sorted(Comparator.comparing(candidate -> candidate.getId().toString()))
                .toList();
    }
}
