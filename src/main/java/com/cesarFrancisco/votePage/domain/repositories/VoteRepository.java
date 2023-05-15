package com.cesarFrancisco.votePage.domain.repositories;

import com.cesarFrancisco.votePage.domain.entities.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}
