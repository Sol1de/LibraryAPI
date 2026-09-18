package com.slain.library.service;

import com.slain.library.exceptions.bookshelf.BookShelfNotFoundException;
import com.slain.library.model.BookShelf;
import com.slain.library.repository.BookShelfRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class BookShelfService {
    private final BookShelfRepository bookShelfRepository;

    public BookShelfService(
            BookShelfRepository bookShelfRepository
    ) {
        this.bookShelfRepository = bookShelfRepository;
    }

    public List<BookShelf> getAll() {
        return this.bookShelfRepository.findAll();
    }

    public BookShelf get(UUID id) throws BookShelfNotFoundException {
        return this.bookShelfRepository.findById(id)
                .orElseThrow(BookShelfNotFoundException::new);
    }

    public BookShelf create(BookShelf bookShelf) {
        return this.bookShelfRepository.save(bookShelf);
    }

    public void delete(UUID id) throws BookShelfNotFoundException {
        var existingShelf = this.bookShelfRepository.findById(id)
                .orElseThrow(BookShelfNotFoundException::new);
        this.bookShelfRepository.delete(existingShelf);
    }
}
