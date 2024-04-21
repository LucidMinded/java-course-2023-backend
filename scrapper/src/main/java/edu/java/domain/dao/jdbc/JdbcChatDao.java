package edu.java.domain.dao.jdbc;

import edu.java.domain.dao.ChatDao;
import edu.java.domain.dao.dto.ChatDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcChatDao implements ChatDao {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Boolean add(Long id) {
        return jdbcTemplate.update("INSERT INTO chat (id) VALUES (?) ON CONFLICT DO NOTHING", id) > 0;
    }

    @Override
    public Boolean remove(Long id) {
        return jdbcTemplate.update("DELETE FROM chat WHERE id = ?", id) > 0;
    }

    @Override
    public ChatDto findById(Long id) {
        return jdbcTemplate.queryForObject(
            "SELECT * FROM chat WHERE id = ?",
            (rs, rowNum) -> new ChatDto(rs.getLong("id")),
            id
        );
    }

    @Override
    public List<ChatDto> findAll() {
        return jdbcTemplate.query("SELECT * FROM chat", (rs, rowNum) -> new ChatDto(rs.getLong("id")));
    }

    @Override
    public Boolean exists(Long id) {
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM chat WHERE id = ?",
            Integer.class,
            id
        );
        return count != null && count > 0;
    }
}
