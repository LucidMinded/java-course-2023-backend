package edu.java.domain.dao.jdbc;

import edu.java.domain.dao.LinkDao;
import edu.java.domain.dao.dto.LinkDto;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

@RequiredArgsConstructor
public class JdbcLinkDao implements LinkDao {
    private final static String ID = "id";
    private final static String URL = "url";
    private final static String UPDATED_AT = "updated_at";
    private final static String LAST_ACTIVITY = "last_activity";
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Boolean add(String url) {
        return jdbcTemplate.update(
            "INSERT INTO link (url, updated_at, last_activity) VALUES (?, ?, ?) ON CONFLICT DO NOTHING",
            url,
            OffsetDateTime.now(),
            OffsetDateTime.now()
        ) > 0;
    } // TODO: maybe need to wrap in timestamp current time

    @Override
    public Boolean remove(String url) {
        return jdbcTemplate.update("DELETE FROM link WHERE url = ?", url) > 0;
    }

    @Override
    public List<LinkDto> findAll() {
        return jdbcTemplate.query(
            "SELECT * FROM link",
            (rs, rowNum) -> new LinkDto(
                rs.getLong(ID),
                rs.getString(URL),
                rs.getObject(UPDATED_AT, OffsetDateTime.class),
                rs.getObject(LAST_ACTIVITY, OffsetDateTime.class)
            )
        );
    }

    @Override
    public LinkDto findById(Long id) {
        return jdbcTemplate.queryForObject(
            "SELECT * FROM link WHERE id = ?",
            (rs, rowNum) -> new LinkDto(
                rs.getLong(ID),
                rs.getString(URL),
                rs.getObject(UPDATED_AT, OffsetDateTime.class),
                rs.getObject(LAST_ACTIVITY, OffsetDateTime.class)
            ),
            id
        );
    }

    @Override
    public LinkDto findByUrl(String url) {
        List<LinkDto> linkDtoList = jdbcTemplate.query(
            "SELECT * FROM link WHERE url = ?",
            (rs, rowNum) -> new LinkDto(
                rs.getLong(ID),
                rs.getString(URL),
                rs.getObject(UPDATED_AT, OffsetDateTime.class),
                rs.getObject(LAST_ACTIVITY, OffsetDateTime.class)
            ),
            url
        );
        return linkDtoList.isEmpty() ? null : linkDtoList.getFirst();
    }

    @Override
    public Collection<LinkDto> findAllByUpdatedBefore(OffsetDateTime updatedBefore) {
        return jdbcTemplate.query(
            "SELECT * FROM link WHERE updated_at < ?",
            (rs, rowNum) -> new LinkDto(
                rs.getLong(ID),
                rs.getString(URL),
                rs.getObject(UPDATED_AT, OffsetDateTime.class),
                rs.getObject(LAST_ACTIVITY, OffsetDateTime.class)
            ),
            updatedBefore
        );
    }

    @Override
    public Boolean update(LinkDto link) {
        return jdbcTemplate.update(
            "UPDATE link SET url = ?, updated_at = ?, last_activity = ? WHERE id = ?",
            link.getUrl(),
            link.getUpdatedAt(),
            link.getLastActivity(),
            link.getId()
        ) > 0;
    }
}
