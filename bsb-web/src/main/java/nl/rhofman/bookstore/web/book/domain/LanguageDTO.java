package nl.rhofman.bookstore.web.book.domain;

public enum LanguageDTO {
    ENGLISH("English"), FRENCH("French"), SPANISH("Spanish"), PORTUGUESE("Portugues"), ITALIAN("Italian"), FINISH("Finish"), GERMAN("German"), DUTCH("Dutch"), RUSSIAN("Russian");

    private String value;

    LanguageDTO(String v) {
        this.value = v;
    }

    public String getValue() {
        return value;
    }

    public static LanguageDTO fromValue(String value) {
        for(LanguageDTO v: LanguageDTO.values()) {
            if (v.value.equals(value)) {
                return v;
            }
        }
        throw new IllegalArgumentException("ShortName [" + value
                + "] not supported.");
    }
}
