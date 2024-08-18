package dev.venketesh.userservice.repository;

import dev.venketesh.userservice.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SessionRepository extends JpaRepository<Token, UUID> {
}
