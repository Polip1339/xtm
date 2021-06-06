package pl.xtm.rekrutacja.exceptions;

import lombok.Getter;

@Getter
public class TranslationNotFoundException extends Throwable {
    String exceptionMessage;

    public TranslationNotFoundException(String s) {
        this.exceptionMessage = s;
    }
}
