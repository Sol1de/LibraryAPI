package com.slain.library.repository;

import com.slain.library.model.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ShelfRepository extends JpaRepository<Shelf, UUID> {
}
