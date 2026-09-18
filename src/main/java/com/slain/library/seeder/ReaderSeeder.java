package com.slain.library.seeder;

import com.slain.library.model.Reader;
import com.slain.library.repository.ReaderRepository;
import com.slain.library.enums.GenderType;
import java.util.List;
import java.util.Objects;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class ReaderSeeder {
    private static final String DEFAULT_PASSWORD = "password123";

    private final ReaderRepository repository;
    private final PasswordEncoder passwordEncoder;

    public ReaderSeeder(ReaderRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Reader> seed() {
        var existing = repository.findAll();
        var first = fixture(existing, "reader@example.com", "Alice", "Martin", GenderType.FEMALE);
        var second = fixture(
                existing,
                "thomas.bernard@example.com",
                "Thomas",
                "Bernard",
                GenderType.MALE
        );
        return repository.saveAll(List.of(first, second));
    }

    private Reader fixture(
            List<Reader> existing,
            String email,
            String firstName,
            String lastName,
            GenderType gender
    ) {
        var emailMatches = existing.stream()
                .filter(candidate -> Objects.equals(candidate.getEmail(), email))
                .toList();
        var legacyMatches = existing.stream()
                .filter(candidate -> isBlank(candidate.getEmail())
                        && Objects.equals(candidate.getFirstName(), firstName)
                        && Objects.equals(candidate.getLastName(), lastName))
                .toList();

        if (emailMatches.size() > 1 || legacyMatches.size() > 1) {
            throw new IllegalStateException("Ambiguous reader fixture: " + email);
        }
        var byEmail = emailMatches.stream().findFirst();
        var legacy = legacyMatches.stream().findFirst();

        if (byEmail.isPresent() && (!Objects.equals(byEmail.get().getFirstName(), firstName)
                || !Objects.equals(byEmail.get().getLastName(), lastName))) {
            throw new IllegalStateException("Fixture email already belongs to another reader: " + email);
        }
        if (byEmail.isPresent() && legacy.isPresent()
                && !Objects.equals(byEmail.get().getId(), legacy.get().getId())) {
            throw new IllegalStateException("Fixture email conflicts with a legacy reader: " + email);
        }

        var reader = legacy
                .or(() -> byEmail)
                .orElseGet(Reader::new);

        reader.setEmail(email);
        reader.setFirstName(firstName);
        reader.setLastName(lastName);
        reader.setGender(gender);
        if (!hasDefaultPassword(reader.getPassword())) {
            reader.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
        }
        return reader;
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private boolean hasDefaultPassword(String value) {
        if (isBlank(value)) {
            return false;
        }
        try {
            return passwordEncoder.matches(DEFAULT_PASSWORD, value);
        } catch (IllegalArgumentException exception) {
            return false;
        }
    }
}
