package com.slain.library.repository;

import com.slain.library.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, UUID> {
    Optional<Reader> findByEmail(String email);
    boolean existsByEmail(String email);
}
