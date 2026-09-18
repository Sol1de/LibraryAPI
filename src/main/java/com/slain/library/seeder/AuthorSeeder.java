package com.slain.library.seeder;

import com.slain.library.model.Author;
import com.slain.library.repository.AuthorRepository;
import com.slain.library.enums.GenderType;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class AuthorSeeder {
    private final AuthorRepository repository;

    public AuthorSeeder(AuthorRepository repository) {
        this.repository = repository;
    }

    public List<Author> getAll() {
        return repository.findAll();
    }

    public boolean isEmpty() {
        return repository.count() == 0;
    }

    public List<Author> seed() {
        Author first = new Author();
        first.setFirstName("Victor");
        first.setLastName("Hugo");
        first.setGender(GenderType.MALE);
        Author second = new Author();
        second.setFirstName("Mary");
        second.setLastName("Shelley");
        second.setGender(GenderType.FEMALE);
        return repository.saveAll(List.of(first, second));
    }
}
