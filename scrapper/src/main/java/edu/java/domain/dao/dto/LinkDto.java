package edu.java.domain.dao.dto;

import edu.java.domain.dao.jpa.model.Link;
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

    public LinkDto(Link link) {
        this.id = link.getId();
        this.url = link.getUrl();
        this.updatedAt = link.getUpdatedAt();
        this.lastActivity = link.getLastActivity();
    }
}
