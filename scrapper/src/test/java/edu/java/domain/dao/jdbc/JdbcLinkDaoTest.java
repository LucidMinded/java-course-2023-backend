package edu.java.domain.dao.jdbc;

import edu.java.domain.dao.dto.LinkDto;
import edu.java.scrapper.IntegrationTest;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource(properties = "app.database-access-type=jdbc")
public class JdbcLinkDaoTest extends IntegrationTest {
    @Autowired
    private JdbcLinkDao linkDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    @Rollback
    void addTest() {
        // Arrange
        String url = "https://github.com/LucidMinded/scrapper_api_test";

        // Act
        linkDao.add(url);

        // Assert
        String queriedUrl = jdbcTemplate.queryForObject(
            "SELECT * FROM link WHERE url = ?",
            (rs, rowNum) -> rs.getString("url"),
            url
        );
        assertEquals(url, queriedUrl);
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        // Arrange
        String url = "https://github.com/LucidMinded/scrapper_api_test";
        linkDao.add(url);

        // Act
        linkDao.remove(url);

        // Assert
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM link WHERE url = ?",
            Integer.class,
            url
        );
        assertEquals(0, count);
    }

    @Test
    @Transactional
    @Rollback
    void findAllTest() {
        // Arrange
        String url1 = "https://github.com/LucidMinded/scrapper_api_test1";
        String url2 = "https://github.com/LucidMinded/scrapper_api_test2";
        linkDao.add(url1);
        linkDao.add(url2);

        // Act
        List<LinkDto> linkDtos = linkDao.findAll();

        // Assert
        assertEquals(2, linkDtos.size());
        assertTrue(linkDtos.stream().anyMatch(linkDto -> linkDto.getUrl().equals(url1)));
        assertTrue(linkDtos.stream().anyMatch(linkDto -> linkDto.getUrl().equals(url2)));
    }

    @Test
    @Transactional
    @Rollback
    void findByIdTest() {
        // Arrange
        String url = "https://github.com/LucidMinded/scrapper_api_test";
        linkDao.add(url);
        Long id = jdbcTemplate.queryForObject(
            "SELECT id FROM link WHERE url = ?",
            Long.class,
            url
        );

        // Act
        LinkDto linkDto = linkDao.findById(id);

        // Assert
        assertEquals(url, linkDto.getUrl());
    }

    @Test
    @Transactional
    @Rollback
    void findByUrlTest() {
        // Arrange
        String url = "https://github.com/LucidMinded/scrapper_api_test";
        linkDao.add(url);

        // Act
        LinkDto linkDto = linkDao.findByUrl(url);

        // Assert
        assertEquals(url, linkDto.getUrl());
    }

    @Test
    @Transactional
    @Rollback
    @SneakyThrows
    void findAllByUpdatedBeforeTest() {
        // Arrange
        String url = "https://github.com/LucidMinded/scrapper_api_test";
        linkDao.add(url);
        OffsetDateTime now = OffsetDateTime.now();
        Thread.sleep(2000);
        linkDao.add("https://github.com/LucidMinded/scrapper_api_test_later");

        // Act
        Collection<LinkDto> linkDtos = linkDao.findAllByUpdatedBefore(now.plusSeconds(1));

        // Assert
        assertEquals(1, linkDtos.size());
        assertTrue(linkDtos.stream().anyMatch(linkDto -> linkDto.getUrl().equals(url)));
    }

    @Test
    @Transactional
    @Rollback
    void updateTest() {
        // Arrange
        String url = "https://github.com/LucidMinded/scrapper_api_test";
        linkDao.add(url);
        Long id = jdbcTemplate.queryForObject(
            "SELECT id FROM link WHERE url = ?",
            Long.class,
            url
        );
        OffsetDateTime timeNow = OffsetDateTime.now();
        LinkDto linkDto = new LinkDto(
            id,
            "https://github.com/LucidMinded/scrapper_api_test_updated",
            timeNow,
            timeNow
        );

        // Act
        linkDao.update(linkDto);

        // Assert
        LinkDto updatedLinkDto = linkDao.findById(id);
        assertEquals(timeNow.toInstant().getEpochSecond(), updatedLinkDto.getUpdatedAt().toInstant().getEpochSecond());
    }
}
