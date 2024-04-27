package edu.java.domain.dao.jooq;

import edu.java.domain.dao.ChatDao;
import edu.java.domain.dao.dto.ChatDto;
import edu.java.scrapper.domain.jooq.tables.records.ChatRecord;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import static edu.java.scrapper.domain.jooq.Tables.CHAT;

@RequiredArgsConstructor
public class JooqChatDao implements ChatDao {
    private final DSLContext dslContext;

    @Override
    public Boolean add(Long id) {
        return dslContext.insertInto(CHAT, CHAT.ID)
            .values(id)
            .onDuplicateKeyIgnore()
            .execute() > 0;
    }

    @Override
    public Boolean remove(Long id) {
        return dslContext.deleteFrom(CHAT)
            .where(CHAT.ID.eq(id))
            .execute() > 0;
    }

    @Override
    public ChatDto findById(Long id) {
        Record dbRecord = dslContext.selectFrom(CHAT)
            .where(CHAT.ID.eq(id))
            .fetchOne();

        return dbRecord != null ? new ChatDto(dbRecord.get(CHAT.ID)) : null;
    }

    @Override
    public List<ChatDto> findAll() {
        @NotNull Result<ChatRecord> records = dslContext.selectFrom(CHAT).fetch();

        return records.stream()
            .map(dbRecord -> new ChatDto(dbRecord.get(CHAT.ID)))
            .collect(Collectors.toList());
    }

    @Override
    public Boolean exists(Long id) {
        return dslContext.fetchExists(
            dslContext.selectFrom(CHAT)
                .where(CHAT.ID.eq(id))
        );
    }
}
