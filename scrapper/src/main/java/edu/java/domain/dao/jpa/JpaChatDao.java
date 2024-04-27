package edu.java.domain.dao.jpa;

import edu.java.domain.dao.jpa.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChatDao extends JpaRepository<Chat, Long> {
}
