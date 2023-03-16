package ua.d1nque.emailsenderexample.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ua.d1nque.emailsenderexample.data.LetterData;
import ua.d1nque.emailsenderexample.dto.LetterSaveDto;
import ua.d1nque.emailsenderexample.data.LetterStatus;
import ua.d1nque.emailsenderexample.exceptions.LetterNotFoundException;
import ua.d1nque.emailsenderexample.exceptions.LetterNotSentException;
import ua.d1nque.emailsenderexample.repository.LetterRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LetterServiceImpl implements LetterService{

    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String sender;
    private final LetterRepository letterRepository;


    @Override
    public LetterData createLetter(LetterSaveDto dto) {
        LetterData letter = new LetterData();
        letter.setSubject(dto.getSubject());
        letter.setContent(dto.getContent());
        letter.setEmail(dto.getEmail());
        letter.setStatus(dto.getStatus());
        return letterRepository.save(letter);
    }

    @Override
    public void processEmailSent(LetterData letter) {
        if (letterRepository.findById(letter.getId()).isPresent()) {
            LetterData letterData = letterRepository.findById(letter.getId()).get();
            sendEmail(letterData);
        }
        else{
            throw new LetterNotFoundException("Letter with id: " + letter.getId() + " not found");
        }
    }


    @Override
    public void resendLetters(){
        List<LetterData> sendingLetters = letterRepository.findByStatus(LetterStatus.SENDING);

        for(LetterData letter : sendingLetters) {
            sendEmail(letter);
        }
    }

    private void sendEmail(LetterData letter){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sender);
        message.setTo(letter.getEmail());
        message.setSubject(letter.getSubject());
        message.setText(letter.getContent());
        try {
            mailSender.send(message);
            letter.setStatus(LetterStatus.SENT);
        } catch (Exception e) {
            letter.setStatus(LetterStatus.ERROR);
            throw new LetterNotSentException("Letter with id: " + letter.getId() + " wasn`t sent");
        }
        letterRepository.save(letter);
    }


}
