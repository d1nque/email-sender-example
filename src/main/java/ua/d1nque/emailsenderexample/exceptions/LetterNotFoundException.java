package ua.d1nque.emailsenderexample.exceptions;

public class LetterNotFoundException extends RuntimeException{

    public LetterNotFoundException(String message){
        super(message);
    }

}
