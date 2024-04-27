package edu.java.domain.dao.jooq;

import edu.java.domain.dao.LinkDao;
import edu.java.domain.dao.dto.LinkDto;
import edu.java.scrapper.domain.jooq.tables.records.LinkRecord;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import static edu.java.scrapper.domain.jooq.Tables.LINK;

@RequiredArgsConstructor
public class JooqLinkDao implements LinkDao {
    private final DSLContext dslContext;

    @Override
    public Boolean add(String url) {
        return dslContext.insertInto(LINK, LINK.URL, LINK.UPDATED_AT, LINK.LAST_ACTIVITY)
            .values(url, OffsetDateTime.now(), OffsetDateTime.now())
            .onDuplicateKeyIgnore()
            .execute() > 0;
    }

    @Override
    public Boolean remove(String url) {
        return dslContext.deleteFrom(LINK)
            .where(LINK.URL.eq(url))
            .execute() > 0;
    }

    @Override
    public List<LinkDto> findAll() {
        @NotNull Result<LinkRecord> records = dslContext.selectFrom(LINK).fetch();

        return records.stream()
            .map(dbRecord -> new LinkDto(
                dbRecord.get(LINK.ID),
                dbRecord.get(LINK.URL),
                dbRecord.get(LINK.UPDATED_AT),
                dbRecord.get(LINK.LAST_ACTIVITY)
            ))
            .collect(Collectors.toList());
    }

    @Override
    public LinkDto findById(Long id) {
        Record dbRecord = dslContext.selectFrom(LINK)
            .where(LINK.ID.eq(id))
            .fetchOne();

        return dbRecord != null ? new LinkDto(
            dbRecord.get(LINK.ID),
            dbRecord.get(LINK.URL),
            dbRecord.get(LINK.UPDATED_AT),
            dbRecord.get(LINK.LAST_ACTIVITY)
        ) : null;
    }

    @Override
    public LinkDto findByUrl(String url) {
        Record dbRecord = dslContext.selectFrom(LINK)
            .where(LINK.URL.eq(url))
            .fetchOne();

        return dbRecord != null ? new LinkDto(
            dbRecord.get(LINK.ID),
            dbRecord.get(LINK.URL),
            dbRecord.get(LINK.UPDATED_AT),
            dbRecord.get(LINK.LAST_ACTIVITY)
        ) : null;
    }

    @Override
    public Collection<LinkDto> findAllByUpdatedBefore(OffsetDateTime updatedBefore) {
        @NotNull Result<LinkRecord> records = dslContext.selectFrom(LINK)
            .where(LINK.UPDATED_AT.lessThan(updatedBefore))
            .fetch();

        return records.stream()
            .map(dbRecord -> new LinkDto(
                dbRecord.get(LINK.ID),
                dbRecord.get(LINK.URL),
                dbRecord.get(LINK.UPDATED_AT),
                dbRecord.get(LINK.LAST_ACTIVITY)
            ))
            .collect(Collectors.toList());
    }

    @Override
    public Boolean update(LinkDto link) {
        return dslContext.update(LINK)
            .set(LINK.URL, link.getUrl())
            .set(LINK.UPDATED_AT, link.getUpdatedAt())
            .set(LINK.LAST_ACTIVITY, link.getLastActivity())
            .where(LINK.ID.eq(link.getId()))
            .execute() > 0;
    }
}
