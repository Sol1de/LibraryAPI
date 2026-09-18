package com.slain.library.seeder;

import com.slain.library.model.Shelf;
import com.slain.library.repository.ShelfRepository;
import com.slain.library.model.BookShelf;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class ShelfSeeder {
    private final ShelfRepository repository;

    public ShelfSeeder(ShelfRepository repository) {
        this.repository = repository;
    }

    public List<Shelf> getAll() {
        return repository.findAll();
    }

    public boolean isEmpty() {
        return repository.count() == 0;
    }

    public List<Shelf> seed(List<BookShelf> bookShelves) {
        return repository.saveAll(bookShelves.stream().map(bookShelf -> {
            Shelf shelf = new Shelf();
            shelf.setBookShelf(bookShelf);
            return shelf;
        }).toList());
    }
}
