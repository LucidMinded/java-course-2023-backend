package edu.java.service.jdbc;

import edu.java.domain.dao.ChatDao;
import edu.java.domain.dao.ChatLinkDao;
import edu.java.service.jdbcjooqcommon.JdbcJooqCommonChatService;

public class JdbcChatService extends JdbcJooqCommonChatService {
    public JdbcChatService(ChatDao chatDao, ChatLinkDao chatLinkDao) {
        super(chatDao, chatLinkDao);
    }
}
