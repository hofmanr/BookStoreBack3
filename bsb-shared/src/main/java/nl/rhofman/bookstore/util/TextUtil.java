package nl.rhofman.bookstore.util;

import jakarta.enterprise.context.Dependent;

@Dependent
public class TextUtil {

    public String sanitize(String textToSanitize) {
        return textToSanitize == null ? null : textToSanitize.replaceAll("\\s+", " ");
    }
}
