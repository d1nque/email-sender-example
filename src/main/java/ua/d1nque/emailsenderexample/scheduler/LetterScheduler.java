package ua.d1nque.emailsenderexample.scheduler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.d1nque.emailsenderexample.service.LetterService;


@EnableScheduling
@Component
public class LetterScheduler
{
    @Autowired
    private LetterService letterService;
    @Scheduled(fixedRate = 300000)
    public void resendLetters(){
        letterService.resendLetters();
    }

}
