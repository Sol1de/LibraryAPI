package com.slain.library.service;

import com.slain.library.exceptions.book.BookNotFoundException;
import com.slain.library.model.Book;
import com.slain.library.repository.BookRepository;
import jakarta.transaction.Transactional;
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

    public List<Book> getBooks() {
        return this.bookRepository.findAll();
    }

    public Book getBook(UUID id) throws BookNotFoundException {
        return this.bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
    }

    public Book createBook(Book book) {
        return this.bookRepository.save(book);
    }

    public Book updateBook(UUID id, Book book) throws BookNotFoundException {
        var existingBook = this.bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        existingBook.update(book);
        return this.bookRepository.save(existingBook);
    }

    public void deleteBook(UUID id) throws BookNotFoundException {
        var existingBook = this.bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        this.bookRepository.delete(existingBook);
    }
}
