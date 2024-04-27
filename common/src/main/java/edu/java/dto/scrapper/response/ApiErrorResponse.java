package edu.java.dto.scrapper.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiErrorResponse {
    @JsonProperty("description")
    private String description;

    @JsonProperty("code")
    private String code;

    @JsonProperty("exceptionName")
    private String exceptionName;

    @JsonProperty("exceptionMessage")
    private String exceptionMessage;

    @JsonProperty("stacktrace")
    @Valid
    private List<String> stacktrace;

    public ApiErrorResponse(String description, String code, Exception e) {
        this.description = description;
        this.code = code;
        this.exceptionName = e.getClass().getName();
        this.exceptionMessage = e.getMessage();
        this.stacktrace = new ArrayList<String>();
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            this.stacktrace.add(stackTraceElement.toString());
        }
    }
}
