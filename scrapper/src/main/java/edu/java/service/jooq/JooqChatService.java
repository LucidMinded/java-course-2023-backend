package edu.java.service.jooq;

import edu.java.domain.dao.ChatDao;
import edu.java.domain.dao.ChatLinkDao;
import edu.java.service.jdbcjooqcommon.JdbcJooqCommonChatService;

public class JooqChatService extends JdbcJooqCommonChatService {
    public JooqChatService(ChatDao chatDao, ChatLinkDao chatLinkDao) {
        super(chatDao, chatLinkDao);
    }
}
