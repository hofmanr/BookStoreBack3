package nl.rhofman.bookstore.persist.model;

public enum Language {
    ENGLISH("EN"), FRENCH("FR"), SPANISH("SP"), PORTUGUESE("PT"), ITALIAN("IT"), FINISH("FI"), GERMAN("DE"), DUTCH("NL"), RUSSIAN("RU");

    private String shortName;

    Language(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public static Language fromShortName(String shortName) {
        for(Language v: Language.values()) {
            if (v.shortName.equals(shortName)) {
                return v;
            }
        }
        throw new IllegalArgumentException("ShortName [" + shortName
                + "] not supported.");
    }
}
