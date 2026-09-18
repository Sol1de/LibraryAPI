package com.slain.library.service;

import com.slain.library.exceptions.bookshelf.BookShelfNotFoundException;
import com.slain.library.exceptions.library.LibraryNotFoundException;
import com.slain.library.model.BookShelf;
import com.slain.library.model.Library;
import com.slain.library.repository.LibraryRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class LibraryService {
    private final LibraryRepository libraryRepository;

    public LibraryService(
            LibraryRepository libraryRepository
    ) {
        this.libraryRepository = libraryRepository;
    }

    public List<Library> getAll() {
        return this.libraryRepository.findAll();
    }

    public Library get(UUID id) throws LibraryNotFoundException {
        return this.libraryRepository.findById(id)
                .orElseThrow(LibraryNotFoundException::new);
    }

    public Library create(Library library) {
        return this.libraryRepository.save(library);
    }

    public Library update(UUID id, Library library) throws LibraryNotFoundException {
        var existingLibrary = this.libraryRepository.findById(id)
                .orElseThrow(LibraryNotFoundException::new);

        existingLibrary.setName(library.getName());
        existingLibrary.setCountry(library.getCountry());
        existingLibrary.setCity(library.getCity());
        existingLibrary.setAddress(library.getAddress());
        existingLibrary.setBookShelf(library.getBookShelf());

        return this.libraryRepository.save(existingLibrary);
    }

    public void delete(UUID id) throws LibraryNotFoundException {
        var existingLibrary = this.libraryRepository.findById(id)
                .orElseThrow(LibraryNotFoundException::new);
        this.libraryRepository.delete(existingLibrary);
    }
}
