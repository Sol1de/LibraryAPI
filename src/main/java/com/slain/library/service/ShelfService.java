package com.slain.library.service;

import com.slain.library.exceptions.shelf.ShelfNotFoundException;
import com.slain.library.model.Shelf;
import com.slain.library.repository.ShelfRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class ShelfService {
    private final ShelfRepository shelfRepository;

    public ShelfService(
            ShelfRepository shelfRepository
    ) {
        this.shelfRepository = shelfRepository;
    }

    public List<Shelf> getAll() {
        return this.shelfRepository.findAll();
    }

    public Shelf get(UUID id) throws ShelfNotFoundException {
        return this.shelfRepository.findById(id)
                .orElseThrow(ShelfNotFoundException::new);
    }

    public Shelf create(Shelf shelf) {
        return this.shelfRepository.save(shelf);
    }

    @Transactional
    public Shelf update(UUID id, Shelf shelf) throws ShelfNotFoundException {
        var existingShelf = this.shelfRepository.findById(id)
                .orElseThrow(ShelfNotFoundException::new);

        existingShelf.setBookShelf(shelf.getBookShelf());
        existingShelf.setBook(shelf.getBook());

        return this.shelfRepository.save(existingShelf);
    }

    @Transactional
    public void delete(UUID id) throws ShelfNotFoundException {
        var existingShelf = this.shelfRepository.findById(id)
                .orElseThrow(ShelfNotFoundException::new);
        this.shelfRepository.delete(existingShelf);
    }
}
