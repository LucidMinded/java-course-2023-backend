package edu.java.dto.bot.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LinkUpdateRequest {
    @JsonProperty("id") private Long id;
    @JsonProperty("url") private String url;
    @JsonProperty("description") private String description;
    @JsonProperty("tgChatIds") private List<Long> tgChatIds;
}
