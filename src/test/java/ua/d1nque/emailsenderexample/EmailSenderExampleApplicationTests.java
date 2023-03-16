package ua.d1nque.emailsenderexample;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ua.d1nque.emailsenderexample.data.LetterData;
import ua.d1nque.emailsenderexample.data.LetterStatus;
import ua.d1nque.emailsenderexample.exceptions.LetterNotFoundException;
import ua.d1nque.emailsenderexample.exceptions.LetterNotSentException;
import ua.d1nque.emailsenderexample.service.LetterService;


@Slf4j
@SpringBootTest
@DirtiesContext
@ActiveProfiles("test")
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
public class EmailSenderExampleApplicationTests {


    @Value("${kafka.topic.emailSent}")
    private String emailSentTopic;
    @Autowired
    KafkaTemplate<String, LetterData> kafkaTemplate;

    @SpyBean
    private LetterService letterService;

    @Test
    void testProcessEmailSentBadLetterId(){
        LetterData letter = new LetterData();
        letter.setStatus(LetterStatus.SENDING);
        letter.setEmail("denisgarkrb@gmail.com");
        letter.setSubject("Subject");
        letter.setId("TIPA ID PRICOL");

        try {
            kafkaTemplate.send(emailSentTopic, letter);
            assertThat(false);
        } catch (LetterNotFoundException e){
            assertThat(e.getMessage().equals("Letter with id: TIPA ID PRICOL not found"));
            assertThat(LetterStatus.ERROR == letter.getStatus());
        }

    }

    @Test
    void testProcessEmailSentBadEmail(){
        LetterData letter = new LetterData();
        letter.setStatus(LetterStatus.SENDING);
        letter.setEmail("denisgarkrb123@gmail.com");
        letter.setSubject("Subject");
        letter.setId("_9pC6IYBJ_vy7WQQXWCS");
        try {
            kafkaTemplate.send(emailSentTopic, letter);
            assertThat(false);
        } catch (LetterNotSentException e){
            assertThat(e.getMessage().equals("Letter with id: TIPA ID PRICOL2 wasn`t sent"));
            assertThat(LetterStatus.ERROR == letter.getStatus());
        }

    }

    @Test
    void testProcessEmailSentPositiveTest(){
        LetterData letter = new LetterData();
        letter.setStatus(LetterStatus.SENDING);
        letter.setEmail("denisgarkrb@gmail.com");
        letter.setSubject("Subject");
        letter.setId("_9pC6IYBJ_vy7WQQXWCS");
        assertThat(LetterStatus.SENT == letter.getStatus());

    }
}

