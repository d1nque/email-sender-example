package ua.d1nque.emailsenderexample.exceptions;

public class LetterNotSentException extends RuntimeException{

    public LetterNotSentException(String message){
        super(message);
    }

}
