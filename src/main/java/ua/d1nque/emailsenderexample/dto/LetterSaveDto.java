package ua.d1nque.emailsenderexample.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import ua.d1nque.emailsenderexample.data.LetterStatus;

@Getter
@Builder
@Jacksonized
public class LetterSaveDto {
    private String subject;
    private String content;
    private String email;
    private LetterStatus status;
}
