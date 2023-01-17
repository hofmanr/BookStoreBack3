package nl.rhofman.bookstore.persist.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import static nl.rhofman.bookstore.persist.model.Language.*;


/**
 * @author Rinus Hofman
 */
@Converter
public class LanguageConverter implements AttributeConverter<Language, String> {

    // ======================================
    // =          Business methods          =
    // ======================================

    @Override
    public String convertToDatabaseColumn(Language language) {
        switch (language) {
            case DUTCH:
                return "NL";
            case ENGLISH:
                return "EN";
            case FINISH:
                return "FI";
            case FRENCH:
                return "FR";
            case GERMAN:
                return "DE";
            case ITALIAN:
                return "IT";
            case PORTUGUESE:
                return "PT";
            case RUSSIAN:
                return "RU";
            case SPANISH:
                return "SP";
            default:
                throw new IllegalArgumentException("Unknown" + language);
        }
    }

    @Override
    public Language convertToEntityAttribute(String dbData) {
        switch (dbData) {
            case "NL":
                return DUTCH;
            case "EN":
                return ENGLISH;
            case "FI":
                return FINISH;
            case "FR":
                return FRENCH;
            case "DE":
                return GERMAN;
            case "IT":
                return ITALIAN;
            case "PT":
                return PORTUGUESE;
            case "RU":
                return RUSSIAN;
            case "SP":
                return SPANISH;
            default:
                throw new IllegalArgumentException("Unknown" + dbData);
        }
    }
}
