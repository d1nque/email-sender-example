package ua.d1nque.emailsenderexample.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ua.d1nque.emailsenderexample.data.LetterData;
import ua.d1nque.emailsenderexample.service.LetterService;

@Component
@RequiredArgsConstructor
public class EmailSentListener {

    private final LetterService letterService;

    @KafkaListener(topics = "${kafka.topic.emailSent}")
    public void emailSent(LetterData letter) {
        letterService.processEmailSent(letter);
    }

}
