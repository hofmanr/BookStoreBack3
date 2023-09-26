package nl.rhofman.bookstore.ejb.catalogue.domain;

public enum Language {
    ENGLISH("English"),
    FRENCH("French"),
    GERMAN("German"),
    ITALIAN("Italian"),
    SPANISH("Spanish"),
    DUTCH("Dutch");
    private final String value;

    Language(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Language fromValue(String v) {
        for (Language c: Language.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
