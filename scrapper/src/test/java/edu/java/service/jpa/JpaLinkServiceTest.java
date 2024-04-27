package edu.java.service.jpa;

import edu.java.domain.dao.dto.LinkDto;
import edu.java.scrapper.IntegrationTest;
import java.net.URI;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = "app.database-access-type=jpa")
public class JpaLinkServiceTest extends IntegrationTest {
    @Autowired
    private JpaLinkService jpaLinkService;

    @Autowired
    private JpaChatService jpaChatService;

    @Test
    public void testAdd() {
        // Arrange
        long chatId = 1L;
        URI url = URI.create("https://github.com/LucidMinded/scrapper_api_test");
        jpaChatService.register(chatId);

        // Act
        LinkDto linkDto = jpaLinkService.add(chatId, url);

        // Assert
        assertThat(linkDto).isNotNull();
        assertThat(linkDto.getUrl()).isEqualTo(url.toString());
    }

    @Test
    public void testRemove() {
        // Arrange
        long chatId = 1L;
        URI url = URI.create("https://github.com/LucidMinded/scrapper_api_test");
        jpaChatService.register(chatId);
        jpaLinkService.add(chatId, url);

        // Act
        LinkDto linkDto = jpaLinkService.remove(chatId, url);

        // Assert
        assertThat(linkDto).isNotNull();
        assertThat(linkDto.getUrl()).isEqualTo(url.toString());
    }

    @Test
    public void testListAllByChatId() {
        // Arrange
        long chatId = 1L;
        URI url = URI.create("https://github.com/LucidMinded/scrapper_api_test");
        jpaChatService.register(chatId);
        jpaLinkService.add(chatId, url);

        // Act
        var result = jpaLinkService.listAllByChatId(chatId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
    }

    @Test
    public void testListAll() {
        // Arrange
        long chatId = 1L;
        URI url = URI.create("https://github.com/LucidMinded/scrapper_api_test");
        jpaChatService.register(chatId);
        jpaLinkService.add(chatId, url);

        // Act
        var result = jpaLinkService.listAll();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
    }

    @Test
    public void testListAllByUpdatedBefore() {
        // Arrange
        long chatId = 1L;
        URI url = URI.create("https://github.com/LucidMinded/scrapper_api_test");
        jpaChatService.register(chatId);
        jpaLinkService.add(chatId, url);
        OffsetDateTime now = OffsetDateTime.now();

        // Act
        var result = jpaLinkService.listAllByUpdatedBefore(now.plusSeconds(1));

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
    }

    @Test
    public void testUpdate() {
        // Arrange
        long chatId = 1L;
        URI url = URI.create("https://github.com/LucidMinded/scrapper_api_test");
        jpaChatService.register(chatId);
        LinkDto linkDto = jpaLinkService.add(chatId, url);
        linkDto.setUrl("https://github.com/LucidMinded/scrapper_api_test_updated");

        // Act
        Boolean result = jpaLinkService.update(linkDto);

        // Assert
        assertThat(result).isTrue();
    }
}
