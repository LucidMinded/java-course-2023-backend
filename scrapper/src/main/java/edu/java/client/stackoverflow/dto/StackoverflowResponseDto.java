package edu.java.client.stackoverflow.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;

@Getter
public class StackoverflowResponseDto {
    @JsonProperty("items") private List<QuestionDto> questions;

    @Override
    public String toString() {
        return "unspecified";
    }
}
