package edu.java.domain.dao.jdbc;

import edu.java.domain.dao.dto.ChatDto;
import edu.java.scrapper.IntegrationTest;
import java.util.List;
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
public class JdbcChatDaoTest extends IntegrationTest {
    @Autowired
    private JdbcChatDao chatDao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @Transactional
    @Rollback
    void addTest() {
        // Arrange
        Long id = 1L;

        // Act
        chatDao.add(id);

        // Assert
        Long queriedId = jdbcTemplate.queryForObject(
            "SELECT id FROM chat WHERE id = ?",
            Long.class,
            id
        );
        assertEquals(id, queriedId);
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        // Arrange
        Long id = 1L;
        chatDao.add(id);

        // Act
        chatDao.remove(id);

        // Assert
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM chat WHERE id = ?",
            Integer.class,
            id
        );
        assertEquals(0, count);
    }

    @Test
    @Transactional
    @Rollback
    void findByIdTest() {
        // Arrange
        Long id = 1L;
        chatDao.add(id);

        // Act
        ChatDto chatDto = chatDao.findById(id);

        // Assert
        assertEquals(id, chatDto.getId());
    }

    @Test
    @Transactional
    @Rollback
    void findAllTest() {
        // Arrange
        Long id1 = 1L;
        Long id2 = 2L;
        chatDao.add(id1);
        chatDao.add(id2);

        // Act
        List<ChatDto> chatDtos = chatDao.findAll();

        // Assert
        assertEquals(2, chatDtos.size());
        assertTrue(chatDtos.stream().anyMatch(chatDto -> chatDto.getId().equals(id1)));
        assertTrue(chatDtos.stream().anyMatch(chatDto -> chatDto.getId().equals(id2)));
    }

    @Test
    @Transactional
    @Rollback
    void existsTest() {
        // Arrange
        Long id = 1L;
        chatDao.add(id);

        // Act
        Boolean exists = chatDao.exists(id);

        // Assert
        assertTrue(exists);
    }
}
