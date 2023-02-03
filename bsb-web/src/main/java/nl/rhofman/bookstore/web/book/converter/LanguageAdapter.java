package nl.rhofman.bookstore.web.book.converter;

import jakarta.json.bind.adapter.JsonbAdapter;
import nl.rhofman.bookstore.web.book.domain.LanguageDTO;

public class LanguageAdapter implements JsonbAdapter<LanguageDTO, String> {
    @Override
    public String adaptToJson(LanguageDTO language) throws Exception {
        return language == null ? null : language.getValue();
    }

    @Override
    public LanguageDTO adaptFromJson(String s) throws Exception {
        return s == null ? null : LanguageDTO.fromValue(s);
    }
}
