package nl.rhofman.bookstore.util;

import jakarta.enterprise.inject.Alternative;
import jakarta.enterprise.inject.Produces;

public class NumberProducer {

    // ======================================
    // =              Producers             =
    // ======================================

    @Produces
    @ThirteenDigits
    private String prefix1 = "13-";

    @Produces
    @ThirteenDigits
    private int postfix1 = 9;

    @Produces
    @EightDigits
    private String prefix2 = "8-";

    @Produces
    @EightDigits
    private int postfix2 = 4;

    @Produces
    @ThirteenDigits
    @EightDigits
    @Alternative
    private String prefix3 = "Mock-";

    @Produces
    @ThirteenDigits
    @EightDigits
    @Alternative
    private int postfix3 = 1;
}
