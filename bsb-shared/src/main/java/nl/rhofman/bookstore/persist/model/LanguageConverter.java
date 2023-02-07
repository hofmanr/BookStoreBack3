package nl.rhofman.bookstore.persist.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Objects;

/**
 * @author Rinus Hofman
 */
@Converter(autoApply = true)
public class LanguageConverter implements AttributeConverter<Language, String> {

    // ======================================
    // =          Business methods          =
    // ======================================

    @Override
    public String convertToDatabaseColumn(Language language) {
        return Objects.isNull(language) ? null : language.getShortName();
    }

    @Override
    public Language convertToEntityAttribute(String dbData) {
        return Objects.isNull(dbData) ? null : Language.fromShortName(dbData);
    }
}
