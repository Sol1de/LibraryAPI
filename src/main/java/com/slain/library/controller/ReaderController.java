package com.slain.library.controller;

import com.slain.library.exceptions.reader.ReaderNotFoundException;
import com.slain.library.model.Reader;
import com.slain.library.service.ReaderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/reader")
public class ReaderController {
    private final ReaderService readerService;

    public ReaderController(
            ReaderService readerService
    ) {
        this.readerService = readerService;
    }

    @GetMapping
    public ResponseEntity<List<Reader>> getAll() {
        return ResponseEntity.ok(this.readerService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reader> get(@PathVariable UUID id) throws ReaderNotFoundException {
        return ResponseEntity.ok(this.readerService.get(id));
    }

    @PostMapping
    public ResponseEntity<Reader> create(@RequestBody Reader reader) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.readerService.create(reader));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reader> update(@PathVariable UUID id, @RequestBody Reader reader) throws ReaderNotFoundException {
        return ResponseEntity.ok(this.readerService.update(id, reader));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable UUID id) throws ReaderNotFoundException {
        this.readerService.delete(id);
        HttpStatus status = HttpStatus.OK;

        return ResponseEntity.status(status).body(Map.of(
                "status", status.value(),
                "message", "Reader deleted successfully"
        ));
    }
}
