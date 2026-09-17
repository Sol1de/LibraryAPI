package com.slain.library.service;

import com.slain.library.exceptions.reader.ReaderNotFoundException;
import com.slain.library.model.Reader;
import com.slain.library.repository.ReaderRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ReaderService {
    private final ReaderRepository readerRepository;

    public ReaderService(
            ReaderRepository readerRepository
    ) {
        this.readerRepository = readerRepository;
    }

    public List<Reader> getAll() {
        return this.readerRepository.findAll();
    }

    public Reader get(UUID id) throws ReaderNotFoundException {
        return this.readerRepository.findById(id)
                .orElseThrow(ReaderNotFoundException::new);
    }

    public Reader create(Reader reader) {
        return this.readerRepository.save(reader);
    }

    public Reader update(UUID id, Reader reader) throws ReaderNotFoundException {
        var existingReader = this.readerRepository.findById(id)
                .orElseThrow(ReaderNotFoundException::new);

        existingReader.setFirstName(reader.getFirstName());
        existingReader.setLastName(reader.getLastName());
        existingReader.setGender(reader.getGender());
        existingReader.setBooks(reader.getBooks());

        return this.readerRepository.save(existingReader);
    }

    public void delete(UUID id) throws ReaderNotFoundException {
        var existingReader = this.readerRepository.findById(id)
                .orElseThrow(ReaderNotFoundException::new);
        this.readerRepository.delete(existingReader);
    }
}
