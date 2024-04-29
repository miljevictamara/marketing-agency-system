package com.bsep.marketingacency.repository;

import com.bsep.marketingacency.model.RejectionNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RejectionNoteRepository extends JpaRepository<RejectionNote, Long> {
    List<RejectionNote> findAll();
}
