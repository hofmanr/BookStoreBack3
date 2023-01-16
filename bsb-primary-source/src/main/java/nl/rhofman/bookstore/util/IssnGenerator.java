package nl.rhofman.bookstore.util;
import jakarta.inject.Inject;

@EightDigits
public class IssnGenerator implements NumberGenerator {

    // ======================================
    // =          Injection Points          =
    // ======================================

    @Inject
    @EightDigits
    private String prefix;

    @Inject
    @EightDigits
    private int postfix;

    // ======================================
    // =          Business methods          =
    // ======================================

    public String generateNumber() {
        return prefix + Math.random() / 1000 + postfix;
    }
}
