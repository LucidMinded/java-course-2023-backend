package edu.java.service.jdbc;

import edu.java.domain.dao.ChatLinkDao;
import edu.java.domain.dao.LinkDao;
import edu.java.domain.dao.dto.LinkDto;
import edu.java.service.LinkService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class JdbcLinkService implements LinkService {
    private final LinkDao linkDao;
    private final ChatLinkDao chatLinkDao;

    @Override
    public LinkDto add(long tgChatId, URI url) {
        linkDao.add(url.toString());
        LinkDto linkDto = linkDao.findByUrl(url.toString());
        chatLinkDao.add(tgChatId, linkDto.getId());
        return linkDto;
    }

    @Override
    public LinkDto remove(long tgChatId, URI url) {
        LinkDto linkDto = linkDao.findByUrl(url.toString());
        if (linkDto == null) {
            return null;
        }
        chatLinkDao.remove(tgChatId, linkDto.getId());
        return linkDto;
    }

    @Override
    public Collection<LinkDto> listAllByChatId(long tgChatId) {
        return chatLinkDao.findLinksByChatId(tgChatId);
    }

    @Override
    public Collection<LinkDto> listAll() {
        return linkDao.findAll();
    }

    @Override
    public Collection<LinkDto> listAllByUpdatedBefore(OffsetDateTime updatedBefore) {
        return linkDao.findAllByUpdatedBefore(updatedBefore);
    }

    @Override
    public Boolean update(LinkDto link) {
        return linkDao.update(link);
    }
}
