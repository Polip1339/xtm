package pl.xtm.rekrutacja.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TextToTranslate {
    // Other fields could be added here in the future, Class could be annotated as @Entity if there was a need to persist it.
    String text;
}
