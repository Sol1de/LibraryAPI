package com.slain.library.seeder;

import com.slain.library.model.Book;
import com.slain.library.repository.BookRepository;
import com.slain.library.model.Author;
import com.slain.library.model.Reader;
import com.slain.library.model.Shelf;
import com.slain.library.enums.BookStatus;
import com.slain.library.enums.BookType;
import java.util.List;
import java.util.Objects;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class BookSeeder {
    private final BookRepository repository;

    public BookSeeder(BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> seed(List<Author> authors, List<Reader> readers, List<Shelf> shelves) {
        var existing = repository.findAll();
        var first = fixture(
                existing,
                "Les Misérables",
                BookType.ROMANCE,
                BookStatus.AVAILABLE,
                authors.get(0),
                null,
                shelves.get(0)
        );

        var second = fixture(
                existing,
                "Notre-Dame de Paris",
                BookType.ROMANCE,
                BookStatus.RENTED,
                authors.get(0),
                readers.get(0),
                shelves.get(0)
        );

        var third = fixture(
                existing,
                "Frankenstein",
                BookType.HORROR,
                BookStatus.RENTED,
                authors.get(1),
                readers.get(1),
                shelves.get(1)
        );
        return repository.saveAll(List.of(first, second, third));
    }

    private Book fixture(
            List<Book> existing,
            String title,
            BookType type,
            BookStatus status,
            Author author,
            Reader reader,
            Shelf shelf
    ) {
        var matches = existing.stream()
                .filter(candidate -> Objects.equals(candidate.getTitle(), title)
                        && candidate.getAuthor() != null
                        && Objects.equals(candidate.getAuthor().getId(), author.getId()))
                .toList();
        if (matches.size() > 1) {
            throw new IllegalStateException(
                    "Ambiguous book fixture: " + title + " by "
                            + author.getFirstName() + " " + author.getLastName());
        }
        var book = matches.isEmpty() ? new Book() : matches.getFirst();
        configure(book, title, type, status, author, reader, shelf);
        return book;
    }

    private void configure(
            Book book,
            String title,
            BookType type,
            BookStatus status,
            Author author,
            Reader reader,
            Shelf shelf
    ) {
        book.setTitle(title);
        book.setType(type);
        book.setStatus(status);
        book.setAuthor(author);
        book.setReader(reader);
        book.setShelf(shelf);
    }
}
