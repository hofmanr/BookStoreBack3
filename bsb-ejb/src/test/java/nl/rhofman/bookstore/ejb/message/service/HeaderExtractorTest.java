package nl.rhofman.bookstore.ejb.message.service;

import nl.rhofman.bookstore.ejb.message.domain.Header;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.CoreMatchers.*;

class HeaderExtractorTest {

    @Test
    void givenRealMessage_whenExtract_thenSuccess() throws IOException {
        // Prepare
        ClassLoader classLoader = HeaderExtractorTest.class.getClassLoader();
        File catalogue = new File(classLoader.getResource("Catalogue.xml").getFile());
        String message = FileUtils.readFileToString(catalogue, "UTF-8");
        HeaderExtractor headerExtractor = new HeaderExtractor();

        // Execute
        Header header = headerExtractor.extract(message);

        // Verify
        assertNotNull(header);
        assertThat(header.getMessageID(), is("20230213203456854"));
        assertThat(header.getCorrelationID(), nullValue());
        assertThat(header.getMessageSender(), is("BookDelivero"));
        assertThat(header.getMessageRecipient(), is("Bookstore"));
        LocalDateTime timeStamp = LocalDateTime.of(2023, 2, 15, 15, 21, 50);
        assertThat(header.getTimestamp(), is(timeStamp));
    }

    @Test
    void givenOnlyDate_whenExtract_thenMidnight() throws IOException {
        // Prepare
        String message = "<Message>\n" +
                "    <header>\n" +
                "        <sender>BookDelivero</sender>\n" +
                "        <recipient>Bookstore</recipient>\n" +
                "        <messageId>20230213203456854</messageId>\n" +
                "        <dateOfPreparation>2023-02-15</dateOfPreparation>\n" +
                "    </header>\n" +
                "    <Body />\n" +
                "</Message>";
        HeaderExtractor headerExtractor = new HeaderExtractor();

        // Execute
        Header header = headerExtractor.extract(message);

        // Verify
        assertNotNull(header);
        assertThat(header.getMessageID(), is("20230213203456854"));
        assertThat(header.getCorrelationID(), nullValue());
        assertThat(header.getMessageSender(), is("BookDelivero"));
        assertThat(header.getMessageRecipient(), is("Bookstore"));
        LocalDateTime timeStamp = LocalDateTime.of(2023, 2, 15, 0, 0, 0);
        assertThat(header.getTimestamp(), is(timeStamp));
    }

    @Test
    void givenNoDateAndTime_whenExtract_thenEmptyDate() throws IOException {
        // Prepare
        String message = "<Message>\n" +
                "    <header>\n" +
                "        <sender>BookDelivero</sender>\n" +
                "        <recipient>Bookstore</recipient>\n" +
                "        <messageId>20230213203456854</messageId>\n" +
                "    </header>\n" +
                "    <Body />\n" +
                "</Message>";
        HeaderExtractor headerExtractor = new HeaderExtractor();

        // Execute
        Header header = headerExtractor.extract(message);

        // Verify
        assertNotNull(header);
        assertThat(header.getMessageID(), is("20230213203456854"));
        assertThat(header.getCorrelationID(), nullValue());
        assertThat(header.getMessageSender(), is("BookDelivero"));
        assertThat(header.getMessageRecipient(), is("Bookstore"));
        assertThat(header.getTimestamp(), nullValue());
    }

}