package edu.java.service.jdbc;

import edu.java.domain.dao.ChatLinkDao;
import edu.java.domain.dao.LinkDao;
import edu.java.service.jdbcjooqcommon.JdbcJooqCommonLinkService;

public class JdbcLinkService extends JdbcJooqCommonLinkService {
    public JdbcLinkService(LinkDao linkDao, ChatLinkDao chatLinkDao) {
        super(linkDao, chatLinkDao);
    }
}
