package edu.java.domain.dao.jooq;

import edu.java.domain.dao.dto.ChatDto;
import edu.java.domain.dao.dto.ChatLinkDto;
import edu.java.domain.dao.dto.LinkDto;
import edu.java.scrapper.IntegrationTest;
import java.util.List;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import static edu.java.scrapper.domain.jooq.Tables.CHAT_LINK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestPropertySource(properties = "app.database-access-type=jooq")
public class JooqChatLinkDaoTest extends IntegrationTest {
    @Autowired
    private JooqChatDao chatDao;
    @Autowired
    private JooqLinkDao linkDao;
    @Autowired
    private JooqChatLinkDao chatLinkDao;
    @Autowired
    private DSLContext dslContext;

    @Test
    @Transactional
    @Rollback
    void addTest() {
        // Arrange
        Long chatId = 1L;
        String url = "https://github.com/LucidMinded/scrapper_api_test";

        // Act
        chatDao.add(chatId);
        linkDao.add(url);
        Long linkId = linkDao.findByUrl(url).getId();
        chatLinkDao.add(chatId, linkId);

        // Assert
        Integer count = dslContext.selectCount()
            .from(CHAT_LINK)
            .where(CHAT_LINK.CHAT_ID.eq(chatId))
            .and(CHAT_LINK.LINK_ID.eq(linkId))
            .fetchOne(0, Integer.class);
        assertEquals(1, count);
    }

    @Test
    @Transactional
    @Rollback
    void removeTest() {
        // Arrange
        Long chatId = 1L;
        String url = "https://github.com/LucidMinded/scrapper_api_test";
        chatDao.add(chatId);
        linkDao.add(url);
        Long linkId = linkDao.findByUrl(url).getId();
        chatLinkDao.add(chatId, linkId);

        // Act
        chatLinkDao.remove(chatId, linkId);

        // Assert
        Integer count = dslContext.selectCount()
            .from(CHAT_LINK)
            .where(CHAT_LINK.CHAT_ID.eq(chatId))
            .and(CHAT_LINK.LINK_ID.eq(linkId))
            .fetchOne(0, Integer.class);
        assertEquals(0, count);
    }

    @Test
    @Transactional
    @Rollback
    void findLinksByChatIdTest() {
        // Arrange
        Long chatId = 1L;
        chatDao.add(chatId);

        String url1 = "https://github.com/LucidMinded/scrapper_api_test1";
        linkDao.add(url1);
        Long linkId1 = linkDao.findByUrl(url1).getId();

        String url2 = "https://github.com/LucidMinded/scrapper_api_test2";
        linkDao.add(url2);
        Long linkId2 = linkDao.findByUrl(url2).getId();

        chatLinkDao.add(chatId, linkId1);
        chatLinkDao.add(chatId, linkId2);

        // Act
        List<LinkDto> linkDtos = chatLinkDao.findLinksByChatId(chatId);

        // Assert
        assertEquals(2, linkDtos.size());
        assertTrue(linkDtos.stream().anyMatch(linkDto -> linkDto.getId().equals(linkId1)));
        assertTrue(linkDtos.stream().anyMatch(linkDto -> linkDto.getId().equals(linkId2)));
    }

    @Test
    @Transactional
    @Rollback
    void findChatsByLinkIdTest() {
        // Arrange
        String url = "https://github.com/LucidMinded/scrapper_api_test";
        linkDao.add(url);
        Long linkId = linkDao.findByUrl(url).getId();

        Long chatId1 = 1L;
        chatDao.add(chatId1);
        Long chatId2 = 2L;
        chatDao.add(chatId2);

        chatLinkDao.add(chatId1, linkId);
        chatLinkDao.add(chatId2, linkId);

        // Act
        List<ChatDto> chatDtos = chatLinkDao.findChatsByLinkId(linkId);

        // Assert
        assertEquals(2, chatDtos.size());
        assertTrue(chatDtos.stream().anyMatch(chatDto -> chatDto.getId().equals(chatId1)));
        assertTrue(chatDtos.stream().anyMatch(chatDto -> chatDto.getId().equals(chatId2)));
    }

    @Test
    @Transactional
    @Rollback
    void findAllTest() {
        // Arrange
        Long chatId1 = 1L;
        chatDao.add(chatId1);
        Long chatId2 = 2L;
        chatDao.add(chatId2);

        String url1 = "https://github.com/LucidMinded/scrapper_api_test1";
        linkDao.add(url1);
        Long linkId1 = linkDao.findByUrl(url1).getId();

        String url2 = "https://github.com/LucidMinded/scrapper_api_test2";
        linkDao.add(url2);
        Long linkId2 = linkDao.findByUrl(url2).getId();

        chatLinkDao.add(chatId1, linkId1);
        chatLinkDao.add(chatId2, linkId2);

        // Act
        List<ChatLinkDto> chatLinkDtos = chatLinkDao.findAll();

        // Assert
        assertEquals(2, chatLinkDtos.size());
        assertTrue(chatLinkDtos.stream().anyMatch(chatLinkDto -> chatLinkDto.getChatId().equals(chatId1) &&
            chatLinkDto.getLinkId().equals(linkId1)));
        assertTrue(chatLinkDtos.stream().anyMatch(chatLinkDto -> chatLinkDto.getChatId().equals(chatId2) &&
            chatLinkDto.getLinkId().equals(linkId2)));
    }
}
