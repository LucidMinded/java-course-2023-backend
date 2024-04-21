package edu.java.client.github.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.io.Serializable;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true, defaultImpl = Void.class)
@JsonSubTypes({@JsonSubTypes.Type(value = RepositoryEventDto.Push.class, name = "PushEvent"),
    @JsonSubTypes.Type(value = RepositoryEventDto.PullRequest.class, name = "PullRequestEvent"),
    @JsonSubTypes.Type(value = RepositoryEventDto.CreateEvent.class, name = "CreateEvent")
})
@Setter
@Getter
@NoArgsConstructor
public abstract class RepositoryEventDto implements Serializable {
    @JsonProperty("id") protected String id;
    @JsonProperty("type") protected String type;
    @JsonProperty("created_at") protected OffsetDateTime createdAt;

    public static class Push extends RepositoryEventDto {
        @JsonProperty("payload") private Payload payload;

        public class Payload {
            @JsonProperty("size") private int size;
        }

    }

    public static class PullRequest extends RepositoryEventDto {
        @JsonProperty("payload") private Payload payload;

        public class Payload {
            @JsonProperty("action") private String action;
        }
    }

    public static class CreateEvent extends RepositoryEventDto {
        @JsonProperty("payload") private Payload payload;

        public class Payload {
            @JsonProperty("ref") private String ref;
        }
    }

}
