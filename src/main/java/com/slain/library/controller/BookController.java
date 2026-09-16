package com.slain.library.controller;

import com.slain.library.exceptions.book.BookNotFoundException;
import com.slain.library.model.Book;
import com.slain.library.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    public BookController(
            BookService bookService
    ) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        return ResponseEntity.ok(this.bookService.getBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable UUID id) throws BookNotFoundException {
        return ResponseEntity.ok(this.bookService.getBook(id));
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.bookService.createBook(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable UUID id, @RequestBody Book book) throws BookNotFoundException {
        return ResponseEntity.ok(this.bookService.updateBook(id, book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteBook(@PathVariable UUID id) throws BookNotFoundException {
        this.bookService.deleteBook(id);
        HttpStatus status = HttpStatus.OK;

        return ResponseEntity.status(status).body(Map.of(
                "status", status.value(),
                "message", "Book deleted successfully"
        ));
    }
}
