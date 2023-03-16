package ua.d1nque.emailsenderexample.service;

import ua.d1nque.emailsenderexample.data.LetterData;
import ua.d1nque.emailsenderexample.dto.LetterSaveDto;

public interface LetterService {

    LetterData createLetter(LetterSaveDto dto);

    void processEmailSent(LetterData letter);

    void resendLetters();
}
