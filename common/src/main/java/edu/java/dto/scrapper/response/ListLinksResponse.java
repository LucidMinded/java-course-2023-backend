package edu.java.dto.scrapper.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ListLinksResponse implements Serializable {
    @JsonProperty("links")
    @Valid
    private List<LinkResponse> links;

    @JsonProperty("size")
    private Integer size;
}
