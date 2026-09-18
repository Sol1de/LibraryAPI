package com.slain.library.service;

import com.slain.library.exceptions.author.AuthorNotFoundException;
import com.slain.library.model.Author;
import com.slain.library.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(
            AuthorRepository authorRepository
    ) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAll() {
        return this.authorRepository.findAll();
    }

    public Author get(UUID id) throws AuthorNotFoundException {
        return this.authorRepository.findById(id)
                .orElseThrow(AuthorNotFoundException::new);
    }

    public Author create(Author author) {
        return this.authorRepository.save(author);
    }

    @Transactional
    public Author update(UUID id, Author author) throws AuthorNotFoundException {
        var existingAuthor = this.authorRepository.findById(id)
                .orElseThrow(AuthorNotFoundException::new);

        existingAuthor.setFirstName(author.getFirstName());
        existingAuthor.setLastName(author.getLastName());
        existingAuthor.setGender(author.getGender());
        existingAuthor.setBooks(author.getBooks());

        return this.authorRepository.save(existingAuthor);
    }

    @Transactional
    public void delete(UUID id) throws AuthorNotFoundException {
        var existingAuthor = this.authorRepository.findById(id)
                .orElseThrow(AuthorNotFoundException::new);
        this.authorRepository.delete(existingAuthor);
    }
}
