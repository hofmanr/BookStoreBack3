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
        switch (shortName) {
            case "NL":
                return Language.DUTCH;
            case "EN":
                return Language.ENGLISH;
            case "FR":
                return Language.FRENCH;
            case "SP":
                return Language.SPANISH;
            default:
                throw new IllegalArgumentException("ShortName [" + shortName
                        + "] not supported.");
        }
    }
}
