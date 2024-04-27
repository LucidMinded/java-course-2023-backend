package edu.java.service.jdbc;

import edu.java.domain.dao.ChatDao;
import edu.java.domain.dao.ChatLinkDao;
import edu.java.domain.dao.dto.ChatDto;
import edu.java.service.ChatService;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class JdbcChatService implements ChatService {
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
