package edu.java.domain.dao.jpa;

import edu.java.domain.dao.jpa.model.Link;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLinkDao extends JpaRepository<Link, Long> {
    Collection<Link> findAllByUpdatedAtBefore(OffsetDateTime updatedBefore);

    Optional<Link> findByUrl(String string);
}
