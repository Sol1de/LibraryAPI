package com.slain.library.seeder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import com.slain.library.model.*;
import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Profile("dev")
@ConditionalOnProperty(name = "seed.enabled", havingValue = "true", matchIfMissing = true)
public class MainSeeder implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainSeeder.class);

    private final AuthorSeeder authorSeeder;
    private final ReaderSeeder readerSeeder;
    private final LibrarySeeder librarySeeder;
    private final BookShelfSeeder bookShelfSeeder;
    private final ShelfSeeder shelfSeeder;
    private final BookSeeder bookSeeder;

    public MainSeeder(
            AuthorSeeder authorSeeder,
            ReaderSeeder readerSeeder,
            LibrarySeeder librarySeeder,
            BookShelfSeeder bookShelfSeeder,
            ShelfSeeder shelfSeeder,
            BookSeeder bookSeeder
    ) {
        this.authorSeeder = authorSeeder;
        this.readerSeeder = readerSeeder;
        this.librarySeeder = librarySeeder;
        this.bookShelfSeeder = bookShelfSeeder;
        this.shelfSeeder = shelfSeeder;
        this.bookSeeder = bookSeeder;
    }

    @Override
    @Transactional
    public void run(String... args) {
        String name = Arrays.stream(args)
                .filter(arg -> arg.startsWith("--seed.name="))
                .map(arg -> arg.substring("--seed.name=".length()))
                .findFirst().orElse("MainSeeder");

        switch (name) {
            case "AuthorSeeder" -> seedAuthors();
            case "ReaderSeeder" -> seedReaders();
            case "LibrarySeeder" -> seedLibrary();
            case "BookShelfSeeder" -> seedBookShelves();
            case "ShelfSeeder" -> seedShelves();
            case "BookSeeder", "MainSeeder" -> seedBooks();
            default -> throw new IllegalArgumentException("Unknown seeder: " + name);
        }
        LOGGER.info("Seeder {} completed; existing data was preserved.", name);
    }

    private List<Author> seedAuthors() {
        return authorSeeder.isEmpty() ? authorSeeder.seed() : authorSeeder.getAll();
    }

    private List<Reader> seedReaders() {
        return readerSeeder.isEmpty() ? readerSeeder.seed() : readerSeeder.getAll();
    }

    private Library seedLibrary() {
        return librarySeeder.isEmpty() ? librarySeeder.seed() : librarySeeder.getAll().getFirst();
    }

    private List<BookShelf> seedBookShelves() {
        return bookShelfSeeder.isEmpty()
                ? bookShelfSeeder.seed(seedLibrary()) : bookShelfSeeder.getAll();
    }

    private List<Shelf> seedShelves() {
        return shelfSeeder.isEmpty()
                ? shelfSeeder.seed(seedBookShelves()) : shelfSeeder.getAll();
    }

    private void seedBooks() {
        // MainSeeder still fills missing parent tables when books already exist.
        var authors = seedAuthors();
        var readers = seedReaders();
        var shelves = seedShelves();
        if (!bookSeeder.isEmpty()) {
            return;
        }
        if (authors.size() < 2 || readers.size() < 2 || shelves.size() < 2) {
            throw new IllegalStateException(
                    "BookSeeder requires at least 2 authors, 2 readers and 2 shelves. "
                    + "Existing tables are not modified; add the missing entities first.");
        }
        bookSeeder.seed(authors, readers, shelves);
    }
}
