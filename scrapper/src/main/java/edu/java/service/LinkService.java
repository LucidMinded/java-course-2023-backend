package edu.java.service;

import edu.java.domain.dao.dto.LinkDto;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.Collection;

public interface LinkService {
    LinkDto add(long tgChatId, URI url);

    LinkDto remove(long tgChatId, URI url);

    Collection<LinkDto> listAllByChatId(long tgChatId);

    Collection<LinkDto> listAll();

    Collection<LinkDto> listAllByUpdatedBefore(OffsetDateTime updatedBefore);

    Boolean update(LinkDto link);
}
