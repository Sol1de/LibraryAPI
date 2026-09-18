package com.slain.library.seeder;

import com.slain.library.model.BookShelf;
import com.slain.library.repository.BookShelfRepository;
import com.slain.library.model.Library;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class BookShelfSeeder {
    private final BookShelfRepository repository;

    public BookShelfSeeder(BookShelfRepository repository) {
        this.repository = repository;
    }

    public List<BookShelf> getAll() {
        return repository.findAll();
    }

    public boolean isEmpty() {
        return repository.count() == 0;
    }

    public List<BookShelf> seed(Library library) {
        BookShelf first = new BookShelf();
        first.setLibrary(library);
        BookShelf second = new BookShelf();
        second.setLibrary(library);
        return repository.saveAll(List.of(first, second));
    }
}
