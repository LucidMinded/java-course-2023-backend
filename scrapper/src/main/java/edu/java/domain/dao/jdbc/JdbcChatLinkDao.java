package edu.java.domain.dao.jdbc;

import edu.java.domain.dao.ChatLinkDao;
import edu.java.domain.dao.dto.ChatDto;
import edu.java.domain.dao.dto.ChatLinkDto;
import edu.java.domain.dao.dto.LinkDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcChatLinkDao implements ChatLinkDao {
    private final JdbcTemplate jdbcTemplate;

    private final static String ID = "id";
    private final static String URL = "url";
    private final static String UPDATED_AT = "updated_at";
    private final static String LAST_ACTIVITY = "last_activity";
    private final static String CHAT_ID = "chat_id";
    private final static String LINK_ID = "link_id";

    @Override
    public Boolean add(Long chatId, Long linkId) {
        return jdbcTemplate.update(
            "INSERT INTO chat_link (chat_id, link_id) VALUES (?, ?) ON CONFLICT DO NOTHING",
            chatId,
            linkId
        ) > 0;
    }

    @Override
    public Boolean remove(Long chatId, Long linkId) {
        return jdbcTemplate.update("DELETE FROM chat_link WHERE chat_id = ? AND link_id = ?", chatId, linkId) > 0;
    }

    @Override
    public List<LinkDto> findLinksByChatId(Long chatId) {
        return jdbcTemplate.query(
            "SELECT l.id, l.url, l.updated_at, l.last_activity FROM link l "
                + "JOIN chat_link cl ON l.id = cl.link_id WHERE cl.chat_id = ?",
            (rs, rowNum) -> new LinkDto(
                rs.getLong(ID),
                rs.getString(URL),
                rs.getObject(UPDATED_AT, java.time.OffsetDateTime.class),
                rs.getObject(LAST_ACTIVITY, java.time.OffsetDateTime.class)
            ),
            chatId
        );
    }

    @Override
    public List<ChatDto> findChatsByLinkId(Long linkId) {
        return jdbcTemplate.query(
            "SELECT c.id FROM chat c JOIN chat_link cl ON c.id = cl.chat_id WHERE cl.link_id = ?",
            (rs, rowNum) -> new ChatDto(
                rs.getLong(ID)),
            linkId
        );
    }

    @Override
    public List<ChatLinkDto> findAll() {
        return jdbcTemplate.query(
            "SELECT * FROM chat_link",
            (rs, rowNum) -> new ChatLinkDto(rs.getLong(CHAT_ID), rs.getLong(LINK_ID))
        );
    }
}
