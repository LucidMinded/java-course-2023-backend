package edu.java.dto.scrapper.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RemoveLinkRequest {
    @JsonProperty("link")
    private String link;
}
