package nl.rhofman.bookstore.ejb.message.domain;

import java.time.LocalDateTime;

public class HeaderStub extends Header {
    public HeaderStub() {
        this.setMessageID("MESSAGE-ID-123");
        this.setCorrelationID("CORRELATION-ID-123");
        this.setTimestamp(LocalDateTime.now());
        this.setMessageSender("SENDER");
        this.setMessageRecipient("RECIPIENT");
    }
}