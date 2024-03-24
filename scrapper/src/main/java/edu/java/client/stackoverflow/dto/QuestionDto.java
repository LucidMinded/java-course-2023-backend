package edu.java.client.stackoverflow.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.OffsetDateTime;
import lombok.Getter;

@Getter
public class QuestionDto implements Serializable {
    @JsonProperty("last_activity_date") private OffsetDateTime lastActivityDate;
}
