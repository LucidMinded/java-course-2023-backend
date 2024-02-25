package edu.java.client.stackoverflow;

import edu.java.client.stackoverflow.dto.StackoverflowResponseDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface StackoverflowClient {
    @GetExchange("/questions/{id}")
    StackoverflowResponseDto getQuestionResponse(@PathVariable("id") String id);
}
