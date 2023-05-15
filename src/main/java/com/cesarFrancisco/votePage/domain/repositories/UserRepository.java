package com.cesarFrancisco.votePage.domain.repositories;

import com.cesarFrancisco.votePage.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
