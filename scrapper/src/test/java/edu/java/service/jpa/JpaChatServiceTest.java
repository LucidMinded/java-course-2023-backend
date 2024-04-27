package edu.java.service.jpa;

import edu.java.domain.dao.jpa.JpaChatDao;
import edu.java.domain.dao.jpa.JpaLinkDao;
import edu.java.domain.dao.jpa.model.Chat;
import edu.java.domain.dao.jpa.model.Link;
import edu.java.scrapper.IntegrationTest;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = "app.database-access-type=jpa")
public class JpaChatServiceTest extends IntegrationTest {
    @Autowired
    private JpaChatDao chatDao;

    @Autowired
    private JpaLinkDao linkDao;

    @Autowired
    private JpaChatService jpaChatService;

    @Test
    public void testRegister() {
        // Arrange
        long id = 1L;

        // Act
        jpaChatService.register(id);

        // Assert
        Optional<Chat> chat = chatDao.findById(id);
        assertThat(chat).isPresent();
    }

    @Test
    public void testUnregister() {
        // Arrange
        long id = 1L;
        jpaChatService.register(id);

        // Act
        jpaChatService.unregister(id);

        // Assert
        Optional<Chat> chat = chatDao.findById(id);
        assertThat(chat).isNotPresent();
    }

    @Test
    public void testListAllByLinkId() {
        // Arrange
        String url = "https://github.com/LucidMinded/scrapper_api_test";
        Link link = new Link();
        OffsetDateTime now = OffsetDateTime.now();
        link.setUrl(url);
        link.setUpdatedAt(now);
        link.setLastActivity(now);
        link = linkDao.save(link);

        // Act
        var result = jpaChatService.listAllByLinkId(link.getId());

        // Assert
        assertThat(result).isNotNull();
    }

    @Test
    public void testIsRegistered() {
        // Arrange
        long id = 1L;
        jpaChatService.register(id);

        // Act
        var result = jpaChatService.isRegistered(id);

        // Assert
        assertThat(result).isTrue();
    }
}
