package ua.d1nque.emailsenderexample.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.d1nque.emailsenderexample.data.LetterData;
import ua.d1nque.emailsenderexample.dto.LetterSaveDto;
import ua.d1nque.emailsenderexample.dto.RestResponse;
import ua.d1nque.emailsenderexample.service.LetterService;

@RestController
@RequestMapping("/emails")
@RequiredArgsConstructor
public class EmailSenderController {

    @Value("${kafka.topic.emailSent}")
    private String emailSentTopic;

    private final KafkaTemplate<Object, LetterData> kafkaTemplate;

    private final LetterService letterService;

    @PostMapping("/send")
    public RestResponse createLetter(@RequestBody LetterSaveDto dto){
        LetterData letter = letterService.createLetter(dto);
        kafkaTemplate.send(emailSentTopic, letter);
        return new RestResponse(letter.getId());
    }

}
