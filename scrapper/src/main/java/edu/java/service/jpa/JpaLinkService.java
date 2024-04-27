package edu.java.service.jpa;

import edu.java.domain.dao.dto.LinkDto;
import edu.java.domain.dao.jpa.JpaChatDao;
import edu.java.domain.dao.jpa.JpaLinkDao;
import edu.java.domain.dao.jpa.model.Chat;
import edu.java.domain.dao.jpa.model.Link;
import edu.java.service.LinkService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaLinkService implements LinkService {
    private final JpaLinkDao linkDao;
    private final JpaChatDao chatDao;

    @Override
    public LinkDto add(long tgChatId, URI url) {
        Chat chat = chatDao.findById(tgChatId).orElse(null);
        if (chat == null) {
            return null;
        }

        Link link = linkDao.findByUrl(url.toString()).orElseGet(() -> {
            Link newLink = new Link();
            newLink.setUrl(url.toString());
            newLink.setLastActivity(OffsetDateTime.now());
            newLink.setUpdatedAt(OffsetDateTime.now());
            return linkDao.save(newLink);
        });

        chat.getLinks().add(link);
        chatDao.save(chat);
        return new LinkDto(link);
    }

    @Override
    public LinkDto remove(long tgChatId, URI url) {
        Chat chat = chatDao.findById(tgChatId).orElse(null);
        Link link = linkDao.findByUrl(url.toString()).orElse(null);
        if (chat == null || link == null) {
            return null;
        }
        chat.getLinks().remove(link);
        chatDao.save(chat);
        return new LinkDto(link);
    }

    @Override
    public Collection<LinkDto> listAllByChatId(long tgChatId) {
        return chatDao.findById(tgChatId)
            .map(Chat::getLinks)
            .map(links -> links.stream().map(LinkDto::new).toList())
            .orElse(List.of());
    }

    @Override
    public Collection<LinkDto> listAll() {
        return linkDao.findAll().stream().map(LinkDto::new).toList();
    }

    @Override
    public Collection<LinkDto> listAllByUpdatedBefore(OffsetDateTime updatedBefore) {
        return linkDao.findAllByUpdatedAtBefore(updatedBefore).stream().map(LinkDto::new).toList();
    }

    @Override
    public Boolean update(LinkDto linkDto) {
        Optional<Link> optionalLink = linkDao.findById(linkDto.getId());
        if (optionalLink.isEmpty()) {
            return false;
        }

        Link link = optionalLink.get();
        link.setUrl(linkDto.getUrl());
        link.setUpdatedAt(OffsetDateTime.now());
        link.setLastActivity(OffsetDateTime.now());
        linkDao.save(link);
        return true;
    }
}
