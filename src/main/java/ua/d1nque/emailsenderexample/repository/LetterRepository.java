package ua.d1nque.emailsenderexample.repository;

import org.springframework.data.repository.CrudRepository;
import ua.d1nque.emailsenderexample.data.LetterData;
import ua.d1nque.emailsenderexample.data.LetterStatus;

import java.util.List;

public interface LetterRepository extends CrudRepository<LetterData, String> {

    List<LetterData> findByStatus(LetterStatus status);

}
