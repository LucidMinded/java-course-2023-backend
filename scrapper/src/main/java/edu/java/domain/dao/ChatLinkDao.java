package edu.java.domain.dao;

import edu.java.domain.dao.dto.ChatDto;
import edu.java.domain.dao.dto.ChatLinkDto;
import edu.java.domain.dao.dto.LinkDto;
import java.util.List;

public interface ChatLinkDao {
    Boolean add(Long chatId, Long linkId);

    Boolean remove(Long chatId, Long linkId);

    List<LinkDto> findLinksByChatId(Long chatId);

    List<ChatDto> findChatsByLinkId(Long linkId);

    List<ChatLinkDto> findAll();
}
