package com.slain.library.service;

import com.slain.library.exceptions.author.AuthorNotFoundException;
import com.slain.library.model.Author;
import com.slain.library.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AuthorService {
    private final AuthorRepository AuthorRepository;

    public AuthorService(
            AuthorRepository AuthorRepository
    ) {
        this.AuthorRepository = AuthorRepository;
    }

    public List<Author> getAll() {
        return this.AuthorRepository.findAll();
    }

    public Author get(UUID id) throws AuthorNotFoundException {
        return this.AuthorRepository.findById(id)
                .orElseThrow(AuthorNotFoundException::new);
    }

    public Author create(Author author) {
        return this.AuthorRepository.save(author);
    }

    public Author update(UUID id, Author author) throws AuthorNotFoundException {
        var existingAuthor = this.AuthorRepository.findById(id)
                .orElseThrow(AuthorNotFoundException::new);

        existingAuthor.setFirstName(author.getFirstName());
        existingAuthor.setLastName(author.getLastName());
        existingAuthor.setGender(author.getGender());
        existingAuthor.setBooks(author.getBooks());

        return this.AuthorRepository.save(existingAuthor);
    }

    public void delete(UUID id) throws AuthorNotFoundException {
        var existingAuthor = this.AuthorRepository.findById(id)
                .orElseThrow(AuthorNotFoundException::new);
        this.AuthorRepository.delete(existingAuthor);
    }
}
