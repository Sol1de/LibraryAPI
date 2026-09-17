package com.slain.library.controller;

import com.slain.library.exceptions.shelf.ShelfNotFoundException;
import com.slain.library.model.Shelf;
import com.slain.library.service.ShelfService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/shelf")
public class ShelfController {
    private final ShelfService shelfService;

    public ShelfController(
            ShelfService shelfService
    ) {
        this.shelfService = shelfService;
    }

    @GetMapping
    public ResponseEntity<List<Shelf>> getAll() {
        return ResponseEntity.ok(this.shelfService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shelf> get(@PathVariable UUID id) throws ShelfNotFoundException {
        return ResponseEntity.ok(this.shelfService.get(id));
    }

    @PostMapping
    public ResponseEntity<Shelf> create(@RequestBody Shelf shelf) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.shelfService.create(shelf));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Shelf> update(@PathVariable UUID id, @RequestBody Shelf shelf) throws ShelfNotFoundException {
        return ResponseEntity.ok(this.shelfService.update(id, shelf));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable UUID id) throws ShelfNotFoundException {
        this.shelfService.delete(id);
        HttpStatus status = HttpStatus.OK;

        return ResponseEntity.status(status).body(Map.of(
                "status", status.value(),
                "message", "Shelf deleted successfully"
        ));
    }
}
