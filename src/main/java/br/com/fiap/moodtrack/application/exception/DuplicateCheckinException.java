package br.com.fiap.moodtrack.application.exception;

public class DuplicateCheckinException extends RuntimeException {
    public DuplicateCheckinException(String message) {
        super(message);
    }
}
