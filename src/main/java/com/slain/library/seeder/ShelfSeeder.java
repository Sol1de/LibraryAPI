package com.slain.library.seeder;

import com.slain.library.model.Shelf;
import com.slain.library.repository.ShelfRepository;
import com.slain.library.model.BookShelf;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class ShelfSeeder {
    private final ShelfRepository repository;

    public ShelfSeeder(ShelfRepository repository) {
        this.repository = repository;
    }

    public List<Shelf> seed(List<BookShelf> bookShelves) {
        var existing = repository.findAll();
        var fixtures = bookShelves.stream().map(bookShelf -> {
            var matches = existing.stream()
                    .filter(candidate -> candidate.getBookShelf() != null
                            && Objects.equals(candidate.getBookShelf().getId(), bookShelf.getId()))
                    .sorted(Comparator.comparing(candidate -> candidate.getId().toString()))
                    .toList();
            if (matches.size() > 1) {
                throw new IllegalStateException(
                        "Ambiguous shelf fixture for bookshelf: " + bookShelf.getId());
            }
            var shelf = matches.isEmpty() ? new Shelf() : matches.getFirst();
            shelf.setBookShelf(bookShelf);
            return shelf;
        }).toList();
        return repository.saveAll(fixtures).stream()
                .sorted(Comparator.comparing(candidate -> candidate.getBookShelf().getId().toString()))
                .toList();
    }
}
