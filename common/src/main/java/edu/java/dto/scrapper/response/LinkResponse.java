package edu.java.dto.scrapper.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.net.URI;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LinkResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("url")
    private String url;
}
