package com.slain.library.service;

import com.slain.library.exceptions.bookshelf.BookShelfNotFoundException;
import com.slain.library.model.BookShelf;
import com.slain.library.repository.BookShelfRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
    public BookShelf update(UUID id, BookShelf bookShelf) throws BookShelfNotFoundException {
        var existingBookShelf = this.bookShelfRepository.findById(id)
                .orElseThrow(BookShelfNotFoundException::new);

        existingBookShelf.setLibrary(bookShelf.getLibrary());
        existingBookShelf.setShelf(bookShelf.getShelf());

        return this.bookShelfRepository.save(existingBookShelf);
    }

    @Transactional
    public void delete(UUID id) throws BookShelfNotFoundException {
        var existingShelf = this.bookShelfRepository.findById(id)
                .orElseThrow(BookShelfNotFoundException::new);
        this.bookShelfRepository.delete(existingShelf);
    }
}
