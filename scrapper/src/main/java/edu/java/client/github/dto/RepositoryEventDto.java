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
    @JsonSubTypes.Type(value = RepositoryEventDto.CreateEvent.class, name = "CreateEvent"),
    @JsonSubTypes.Type(value = RepositoryEventDto.IssuesEvent.class, name = "IssuesEvent")
})
@Setter
@Getter
@NoArgsConstructor
@SuppressWarnings("MultipleStringLiterals")
public abstract class RepositoryEventDto implements Serializable {
    @JsonProperty("id") protected String id;
    @JsonProperty("type") protected String type;
    @JsonProperty("created_at") protected OffsetDateTime createdAt;

    public static class Push extends RepositoryEventDto {
        @JsonProperty("payload") private Payload payload;

        @Override
        public String toString() {
            return "Pushed " + payload.size + " commits";
        }

        public static class Payload {
            @JsonProperty("size") private int size;
        }
    }

    public static class PullRequest extends RepositoryEventDto {
        @JsonProperty("payload") private Payload payload;

        @Override
        public String toString() {
            return "Pull request " + payload.action
                + "\nURL: " + payload.pullRequestInPayload.htmlUrl;
        }

        public static class Payload {
            @JsonProperty("action") private String action;
            @JsonProperty("pull_request") private PullRequestInPayload pullRequestInPayload;

            public static class PullRequestInPayload {
                @JsonProperty("html_url") private String htmlUrl;
            }
        }
    }

    public static class CreateEvent extends RepositoryEventDto {
        @JsonProperty("payload") private Payload payload;

        @Override
        public String toString() {
            return "Created " + payload.refType;
        }

        public static class Payload {
            @JsonProperty("ref_type") private String refType;
        }
    }

    public static class IssuesEvent extends RepositoryEventDto {
        @JsonProperty("payload") private Payload payload;

        @Override
        public String toString() {
            return "Issue " + payload.action
                + "\nURL: " + payload.issue.htmlUrl;
        }

        public static class Payload {
            @JsonProperty("action") private String action;
            @JsonProperty("issue") private Issue issue;

            public static class Issue {
                @JsonProperty("html_url") private String htmlUrl;
            }
        }
    }
}
