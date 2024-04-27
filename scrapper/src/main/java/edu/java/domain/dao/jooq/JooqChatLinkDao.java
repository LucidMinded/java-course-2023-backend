package edu.java.domain.dao.jooq;

import edu.java.domain.dao.ChatLinkDao;
import edu.java.domain.dao.dto.ChatDto;
import edu.java.domain.dao.dto.ChatLinkDto;
import edu.java.domain.dao.dto.LinkDto;
import edu.java.scrapper.domain.jooq.tables.records.ChatLinkRecord;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import static edu.java.scrapper.domain.jooq.Tables.CHAT_LINK;
import static edu.java.scrapper.domain.jooq.Tables.LINK;

@RequiredArgsConstructor
public class JooqChatLinkDao implements ChatLinkDao {
    private final DSLContext dslContext;

    @Override
    public Boolean add(Long chatId, Long linkId) {
        return dslContext.insertInto(CHAT_LINK, CHAT_LINK.CHAT_ID, CHAT_LINK.LINK_ID)
            .values(chatId, linkId)
            .onDuplicateKeyIgnore()
            .execute() > 0;
    }

    @Override
    public Boolean remove(Long chatId, Long linkId) {
        return dslContext.deleteFrom(CHAT_LINK)
            .where(CHAT_LINK.CHAT_ID.eq(chatId))
            .and(CHAT_LINK.LINK_ID.eq(linkId))
            .execute() > 0;
    }

    @Override
    public List<LinkDto> findLinksByChatId(Long chatId) {
        @NotNull Result<Record> records = dslContext.select()
            .from(LINK)
            .join(CHAT_LINK).on(LINK.ID.eq(CHAT_LINK.LINK_ID))
            .where(CHAT_LINK.CHAT_ID.eq(chatId))
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
    public List<ChatDto> findChatsByLinkId(Long linkId) {
        @NotNull Result<ChatLinkRecord> records = dslContext.selectFrom(CHAT_LINK)
            .where(CHAT_LINK.LINK_ID.eq(linkId))
            .fetch();

        return records.stream()
            .map(dbRecord -> new ChatDto(dbRecord.get(CHAT_LINK.CHAT_ID)))
            .collect(Collectors.toList());
    }

    @Override
    public List<ChatLinkDto> findAll() {
        @NotNull Result<ChatLinkRecord> records = dslContext.selectFrom(CHAT_LINK).fetch();

        return records.stream()
            .map(dbRecord -> new ChatLinkDto(dbRecord.get(CHAT_LINK.CHAT_ID), dbRecord.get(CHAT_LINK.LINK_ID)))
            .collect(Collectors.toList());
    }
}
