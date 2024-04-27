package edu.java.service.jdbcjooqcommon;

import edu.java.domain.dao.ChatDao;
import edu.java.domain.dao.ChatLinkDao;
import edu.java.domain.dao.dto.ChatDto;
import edu.java.service.ChatService;
import java.util.Collection;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JdbcJooqCommonChatService implements ChatService {
    private final ChatDao chatDao;
    private final ChatLinkDao chatLinkDao;

    @Override
    public void register(long tgChatId) {
        chatDao.add(tgChatId);
    }

    @Override
    public void unregister(long tgChatId) {
        chatDao.remove(tgChatId);
    }

    @Override
    public Collection<ChatDto> listAllByLinkId(long linkId) {
        return chatLinkDao.findChatsByLinkId(linkId);
    }

    @Override
    public Boolean isRegistered(long tgChatId) {
        return chatDao.exists(tgChatId);
    }
}

