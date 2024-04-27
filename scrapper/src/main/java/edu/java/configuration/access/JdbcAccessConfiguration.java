package edu.java.configuration.access;

import edu.java.domain.dao.jdbc.JdbcChatDao;
import edu.java.domain.dao.jdbc.JdbcChatLinkDao;
import edu.java.domain.dao.jdbc.JdbcLinkDao;
import edu.java.service.jdbc.JdbcChatService;
import edu.java.service.jdbc.JdbcLinkService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {
    @Bean
    public JdbcChatDao chatDao(JdbcTemplate jdbcTemplate) {
        return new JdbcChatDao(jdbcTemplate);
    }

    @Bean
    public JdbcLinkDao linkDao(JdbcTemplate jdbcTemplate) {
        return new JdbcLinkDao(jdbcTemplate);
    }

    @Bean
    public JdbcChatLinkDao chatLinkDao(JdbcTemplate jdbcTemplate) {
        return new JdbcChatLinkDao(jdbcTemplate);
    }

    @Bean
    public JdbcChatService chatService(
        JdbcChatDao chatDao,
        JdbcChatLinkDao chatLinkDao
    ) {
        return new JdbcChatService(chatDao, chatLinkDao);
    }

    @Bean
    public JdbcLinkService linkService(
        JdbcLinkDao linkDao,
        JdbcChatLinkDao chatLinkDao
    ) {
        return new JdbcLinkService(linkDao, chatLinkDao);
    }
}
