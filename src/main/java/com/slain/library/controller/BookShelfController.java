package com.slain.library.controller;

import com.slain.library.exceptions.bookshelf.BookShelfNotFoundException;
import com.slain.library.model.BookShelf;
import com.slain.library.service.BookShelfService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/bookshelf")
public class BookShelfController {
    private final BookShelfService bookShelfService;

    public BookShelfController(
            BookShelfService bookShelfService
    ) {
        this.bookShelfService = bookShelfService;
    }

    @GetMapping
    public ResponseEntity<List<BookShelf>> getAll() {
        return ResponseEntity.ok(this.bookShelfService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookShelf> get(@PathVariable UUID id) throws BookShelfNotFoundException {
        return ResponseEntity.ok(this.bookShelfService.get(id));
    }

    @PostMapping
    public ResponseEntity<BookShelf> create(@RequestBody BookShelf bookShelf) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.bookShelfService.create(bookShelf));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookShelf> update(@PathVariable UUID id, @RequestBody BookShelf bookShelf) throws BookShelfNotFoundException {
        return ResponseEntity.ok(this.bookShelfService.update(id, bookShelf));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable UUID id) throws BookShelfNotFoundException {
        this.bookShelfService.delete(id);
        HttpStatus status = HttpStatus.OK;

        return ResponseEntity.status(status).body(Map.of(
                "status", status.value(),
                "message", "Bookshelf deleted successfully"
        ));
    }
}
