package edu.java.domain.dao;

import edu.java.domain.dao.dto.LinkDto;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;

public interface LinkDao {
    Boolean add(String url);

    Boolean remove(String url);

    List<LinkDto> findAll();

    LinkDto findById(Long id);

    LinkDto findByUrl(String url);

    Collection<LinkDto> findAllByUpdatedBefore(OffsetDateTime updatedBefore);

    Boolean update(LinkDto link);
}
