package com.slain.library.service;

import com.slain.library.enums.BookStatus;
import com.slain.library.exceptions.reader.ReaderNotFoundException;
import com.slain.library.model.Reader;
import com.slain.library.repository.ReaderRepository;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.nio.charset.StandardCharsets;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class ReaderService {
    private final ReaderRepository readerRepository;
    private final PasswordEncoder passwordEncoder;

    public ReaderService(
            ReaderRepository readerRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.readerRepository = readerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Reader getCurrent(Authentication authentication) {
        return this.readerRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Reader account unavailable"));
    }

    public List<Reader> getAll(Authentication authentication) {
        return List.of(getCurrent(authentication));
    }

    public Reader get(UUID id, Authentication authentication) throws ReaderNotFoundException {
        var reader = getCurrent(authentication);
        if (!reader.getId().equals(id)) throw new ReaderNotFoundException();
        return reader;
    }

    public Reader create(Reader reader) {
        var email = reader.getEmail();
        var password = reader.getPassword();
        if (email == null || !email.matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+")
                || password == null || password.length() < 8
                || password.getBytes(StandardCharsets.UTF_8).length > 72) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Valid email and password of at least 8 characters required");
        }
        if (readerRepository.existsByEmail(email)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Email already registered");
        }
        reader.setBooks(null);
        reader.setPassword(passwordEncoder.encode(password));
        return this.readerRepository.save(reader);
    }

    @Transactional
    public Reader update(UUID id, Reader reader, Authentication authentication) throws ReaderNotFoundException {
        var existingReader = get(id, authentication);

        existingReader.setFirstName(reader.getFirstName());
        existingReader.setLastName(reader.getLastName());
        existingReader.setGender(reader.getGender());

        return this.readerRepository.save(existingReader);
    }

    @Transactional
    public void delete(UUID id, Authentication authentication) throws ReaderNotFoundException {
        var existingReader = get(id, authentication);
        var readerBooks = existingReader.getBooks();

        if (readerBooks != null) readerBooks.forEach(book -> {
            book.setReader(null);
            book.setStatus(BookStatus.LOST);
            book.setShelf(null);
        });

        this.readerRepository.delete(existingReader);
    }
}
