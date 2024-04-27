package edu.java.domain.dao;

import edu.java.domain.dao.dto.ChatDto;
import java.util.List;

public interface ChatDao {
    Boolean add(Long id);

    Boolean remove(Long id);

    ChatDto findById(Long id);

    List<ChatDto> findAll();

    Boolean exists(Long id);
}
