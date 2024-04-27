package edu.java.service.jooq;

import edu.java.domain.dao.ChatLinkDao;
import edu.java.domain.dao.LinkDao;
import edu.java.service.jdbcjooqcommon.JdbcJooqCommonLinkService;

public class JooqLinkService extends JdbcJooqCommonLinkService {
    public JooqLinkService(LinkDao linkDao, ChatLinkDao chatLinkDao) {
        super(linkDao, chatLinkDao);
    }
}
