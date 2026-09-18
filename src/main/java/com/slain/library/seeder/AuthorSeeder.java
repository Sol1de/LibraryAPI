package com.slain.library.seeder;

import com.slain.library.model.Author;
import com.slain.library.repository.AuthorRepository;
import com.slain.library.enums.GenderType;
import java.util.List;
import java.util.Objects;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class AuthorSeeder {
    private final AuthorRepository repository;

    public AuthorSeeder(AuthorRepository repository) {
        this.repository = repository;
    }

    public List<Author> seed() {
        var existing = repository.findAll();
        var first = fixture(existing, "Victor", "Hugo", GenderType.MALE);
        var second = fixture(existing, "Mary", "Shelley", GenderType.FEMALE);
        return repository.saveAll(List.of(first, second));
    }

    private Author fixture(List<Author> existing, String firstName, String lastName, GenderType gender) {
        var matches = existing.stream()
                .filter(candidate -> Objects.equals(candidate.getFirstName(), firstName)
                        && Objects.equals(candidate.getLastName(), lastName))
                .toList();
        if (matches.size() > 1) {
            throw new IllegalStateException("Ambiguous author fixture: " + firstName + " " + lastName);
        }
        var author = matches.isEmpty() ? new Author() : matches.getFirst();
        author.setFirstName(firstName);
        author.setLastName(lastName);
        author.setGender(gender);
        return author;
    }
}
