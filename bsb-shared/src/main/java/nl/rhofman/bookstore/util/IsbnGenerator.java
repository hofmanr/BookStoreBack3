package nl.rhofman.bookstore.util;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import java.util.Random;

@ThirteenDigits
@Dependent
public class IsbnGenerator implements NumberGenerator {
    // ======================================
    // =          Injection Points          =
    // ======================================

    @Inject
    @ThirteenDigits
    private String prefix;

    @Inject
    @ThirteenDigits
    private int postfix;


    @Override
    public String generateNumber() {
        return prefix + "5677-" + Math.abs(new Random().nextInt()) + postfix;
    }
}
