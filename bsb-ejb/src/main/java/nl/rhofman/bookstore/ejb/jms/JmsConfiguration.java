package nl.rhofman.bookstore.ejb.jms;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.jms.JMSConnectionFactory;
import jakarta.jms.JMSContext;

@Dependent
public class JmsConfiguration {
    @Produces
    @JMSConnectionFactory("java:comp/env/jms/bookstoreCF")
    private JMSContext jmsContext;
}
