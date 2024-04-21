package edu.java.domain.dao.dto;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LinkDto {
    private Long id;
    private String url;
    private OffsetDateTime updatedAt;
    private OffsetDateTime lastActivity;

    public LinkDto(LinkDto linkDto) {
        this.id = linkDto.getId();
        this.url = linkDto.getUrl();
        this.updatedAt = linkDto.getUpdatedAt();
        this.lastActivity = linkDto.getLastActivity();
    }
}
