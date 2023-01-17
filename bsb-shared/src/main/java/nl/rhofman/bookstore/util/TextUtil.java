package nl.rhofman.bookstore.util;

public class TextUtil {

    public String sanitize(String textToSanitize) {
        return textToSanitize == null ? null : textToSanitize.replaceAll("\\s+", " ");
    }
}
