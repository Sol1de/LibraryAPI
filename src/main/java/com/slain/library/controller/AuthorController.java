package com.slain.library.controller;

import com.slain.library.exceptions.author.AuthorNotFoundException;
import com.slain.library.model.Author;
import com.slain.library.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/author")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(
            AuthorService authorService
    ) {
        this.authorService = authorService;
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAll() {
        return ResponseEntity.ok(this.authorService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> get(@PathVariable UUID id) throws AuthorNotFoundException {
        return ResponseEntity.ok(this.authorService.get(id));
    }

    @PostMapping
    public ResponseEntity<Author> create(@RequestBody Author author) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.authorService.create(author));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> update(@PathVariable UUID id, @RequestBody Author author) throws AuthorNotFoundException {
        return ResponseEntity.ok(this.authorService.update(id, author));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable UUID id) throws AuthorNotFoundException {
        this.authorService.delete(id);
        HttpStatus status = HttpStatus.OK;

        return ResponseEntity.status(status).body(Map.of(
                "status", status.value(),
                "message", "Author deleted successfully"
        ));
    }
}
