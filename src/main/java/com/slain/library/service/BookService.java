package com.slain.library.service;

import com.slain.library.exceptions.book.BookNotFoundException;
import com.slain.library.model.Book;
import com.slain.library.repository.BookRepository;
import org.springframework.stereotype.Service;
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
        return this.bookRepository.save(book);
    }

    public Book update(UUID id, Book book) throws BookNotFoundException {
        var existingBook = this.bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);

        existingBook.setTitle(book.getTitle());
        existingBook.setType(book.getType());
        existingBook.setStatus(book.getStatus());
        existingBook.setAuthor(book.getAuthor());

        return this.bookRepository.save(existingBook);
    }

    public void delete(UUID id) throws BookNotFoundException {
        var existingBook = this.bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        this.bookRepository.delete(existingBook);
    }
}
