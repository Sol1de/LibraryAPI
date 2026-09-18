package com.slain.library.seeder;

import com.slain.library.model.Reader;
import com.slain.library.repository.ReaderRepository;
import com.slain.library.enums.GenderType;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class ReaderSeeder {
    private final ReaderRepository repository;

    public ReaderSeeder(ReaderRepository repository) {
        this.repository = repository;
    }

    public List<Reader> getAll() {
        return repository.findAll();
    }

    public boolean isEmpty() {
        return repository.count() == 0;
    }

    public List<Reader> seed() {
        Reader first = new Reader();
        first.setFirstName("Alice");
        first.setLastName("Martin");
        first.setGender(GenderType.FEMALE);
        Reader second = new Reader();
        second.setFirstName("Thomas");
        second.setLastName("Bernard");
        second.setGender(GenderType.MALE);
        return repository.saveAll(List.of(first, second));
    }
}
