package edu.java.configuration.access;

import edu.java.domain.dao.jooq.JooqChatDao;
import edu.java.domain.dao.jooq.JooqChatLinkDao;
import edu.java.domain.dao.jooq.JooqLinkDao;
import edu.java.service.jooq.JooqChatService;
import edu.java.service.jooq.JooqLinkService;
import org.jooq.DSLContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
public class JooqAccessConfiguration {
    @Bean
    public JooqChatDao chatDao(DSLContext dslContext) {
        return new JooqChatDao(dslContext);
    }

    @Bean
    public JooqLinkDao linkDao(DSLContext dslContext) {
        return new JooqLinkDao(dslContext);
    }

    @Bean
    public JooqChatLinkDao chatLinkDao(DSLContext dslContext) {
        return new JooqChatLinkDao(dslContext);
    }

    @Bean
    public JooqChatService chatService(
        JooqChatDao chatDao,
        JooqChatLinkDao chatLinkDao
    ) {
        return new JooqChatService(chatDao, chatLinkDao);
    }

    @Bean
    public JooqLinkService linkService(
        JooqLinkDao linkDao,
        JooqChatLinkDao chatLinkDao
    ) {
        return new JooqLinkService(linkDao, chatLinkDao);
    }
}
