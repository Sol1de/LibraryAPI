package com.slain.library.controller;

import com.slain.library.exceptions.library.LibraryNotFoundException;
import com.slain.library.model.Library;
import com.slain.library.service.LibraryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/library")
public class LibraryController {
    private final LibraryService libraryService;

    public LibraryController(
            LibraryService libraryService
    ) {
        this.libraryService = libraryService;
    }

    @GetMapping
    public ResponseEntity<List<Library>> getAll() {
        return ResponseEntity.ok(this.libraryService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Library> get(@PathVariable UUID id) throws LibraryNotFoundException {
        return ResponseEntity.ok(this.libraryService.get(id));
    }

    @PostMapping
    public ResponseEntity<Library> create(@RequestBody Library library) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.libraryService.create(library));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Library> update(@PathVariable UUID id, @RequestBody Library library) throws LibraryNotFoundException {
        return ResponseEntity.ok(this.libraryService.update(id, library));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable UUID id) throws LibraryNotFoundException {
        this.libraryService.delete(id);
        HttpStatus status = HttpStatus.OK;

        return ResponseEntity.status(status).body(Map.of(
                "status", status.value(),
                "message", "Library deleted successfully"
        ));
    }
}
