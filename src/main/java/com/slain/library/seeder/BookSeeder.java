package com.slain.library.seeder;

import com.slain.library.model.Book;
import com.slain.library.repository.BookRepository;
import com.slain.library.model.Author;
import com.slain.library.model.Reader;
import com.slain.library.model.Shelf;
import com.slain.library.enums.BookStatus;
import com.slain.library.enums.BookType;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class BookSeeder {
    private final BookRepository repository;

    public BookSeeder(BookRepository repository) {
        this.repository = repository;
    }

    public boolean isEmpty() {
        return repository.count() == 0;
    }

    public List<Book> seed(List<Author> authors, List<Reader> readers, List<Shelf> shelves) {
        Book first = new Book();
        first.setTitle("Les Misérables");
        first.setType(BookType.ROMANCE);
        first.setStatus(BookStatus.AVAILABLE);
        first.setAuthor(authors.get(0));
        first.setShelf(shelves.get(0));

        Book second = new Book();
        second.setTitle("Notre-Dame de Paris");
        second.setType(BookType.ROMANCE);
        second.setStatus(BookStatus.RENTED);
        second.setAuthor(authors.get(0));
        second.setReader(readers.get(0));
        second.setShelf(shelves.get(0));

        Book third = new Book();
        third.setTitle("Frankenstein");
        third.setType(BookType.HORROR);
        third.setStatus(BookStatus.RENTED);
        third.setAuthor(authors.get(1));
        third.setReader(readers.get(1));
        third.setShelf(shelves.get(1));
        return repository.saveAll(List.of(first, second, third));
    }
}
