package edu.java.configuration.access;

import edu.java.domain.dao.jpa.JpaChatDao;
import edu.java.domain.dao.jpa.JpaLinkDao;
import edu.java.service.jpa.JpaChatService;
import edu.java.service.jpa.JpaLinkService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {
    @Bean
    public JpaChatService chatService(
        JpaChatDao chatDao,
        JpaLinkDao linkDao
    ) {
        return new JpaChatService(chatDao, linkDao);
    }

    @Bean
    public JpaLinkService linkService(
        JpaLinkDao linkDao,
        JpaChatDao chatDao
    ) {
        return new JpaLinkService(linkDao, chatDao);
    }
}
