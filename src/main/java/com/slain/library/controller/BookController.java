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
    public ResponseEntity<List<Book>> getAll() {
        return ResponseEntity.ok(this.bookService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> get(@PathVariable UUID id) throws BookNotFoundException {
        return ResponseEntity.ok(this.bookService.get(id));
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.bookService.create(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable UUID id, @RequestBody Book book) throws BookNotFoundException {
        return ResponseEntity.ok(this.bookService.update(id, book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable UUID id) throws BookNotFoundException {
        this.bookService.delete(id);
        HttpStatus status = HttpStatus.OK;

        return ResponseEntity.status(status).body(Map.of(
                "status", status.value(),
                "message", "Book deleted successfully"
        ));
    }
}
