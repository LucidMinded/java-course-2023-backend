package edu.java.service;

import edu.java.domain.dao.dto.ChatDto;
import java.util.Collection;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ChatService {
    void register(long tgChatId);

    void unregister(long tgChatId);

    Collection<ChatDto> listAllByLinkId(long linkId);

    Boolean isRegistered(long tgChatId);
}
