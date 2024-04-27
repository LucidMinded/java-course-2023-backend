package edu.java.service.jpa;

import edu.java.domain.dao.dto.ChatDto;
import edu.java.domain.dao.jpa.JpaChatDao;
import edu.java.domain.dao.jpa.JpaLinkDao;
import edu.java.domain.dao.jpa.model.Chat;
import edu.java.domain.dao.jpa.model.Link;
import edu.java.service.ChatService;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaChatService implements ChatService {
    private final JpaChatDao chatDao;
    private final JpaLinkDao linkDao;

    @Override
    public void register(long tgChatId) {
        Chat chat = chatDao.findById(tgChatId).orElse(null);
        if (chat != null) {
            return;
        }
        chatDao.save(new Chat(tgChatId, null));
    }

    @Override
    public void unregister(long tgChatId) {
        chatDao.deleteById(tgChatId);
    }

    @Override
    public Collection<ChatDto> listAllByLinkId(long linkId) {
        Optional<Link> link = linkDao.findById(linkId);
        Set<Chat> chats = link.map(Link::getChats).orElse(Set.of());
        return chats.stream().map(ChatDto::new).toList();
    }

    @Override
    public Boolean isRegistered(long tgChatId) {
        return chatDao.existsById(tgChatId);
    }
}
