package edu.java.domain.dao.dto;

import edu.java.domain.dao.jpa.model.Chat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {
    private Long id;

    public ChatDto(Chat chat) {
        this.id = chat.getId();
    }
}
