package com.slain.library.service;

import com.slain.library.exceptions.book.BookNotFoundException;
import com.slain.library.model.Book;
import com.slain.library.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(
            BookRepository bookRepository
    ) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAll() {
        return this.bookRepository.findAll();
    }

    public Book get(UUID id) throws BookNotFoundException {
        return this.bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
    }

    public Book create(Book book) {
        var created = new Book();
        copyFields(book, created);
        return this.bookRepository.save(created);
    }

    @Transactional
    public Book update(UUID id, Book book) throws BookNotFoundException {
        var existingBook = get(id);
        copyFields(book, existingBook);
        return this.bookRepository.save(existingBook);
    }

    @Transactional
    public void delete(UUID id) throws BookNotFoundException {
        var existingBook = get(id);
        this.bookRepository.delete(existingBook);
    }

    private void copyFields(Book source, Book target) {
        target.setTitle(source.getTitle());
        target.setType(source.getType());
        target.setStatus(source.getStatus());
        target.setAuthor(source.getAuthor());
        target.setShelf(source.getShelf());
    }
}
