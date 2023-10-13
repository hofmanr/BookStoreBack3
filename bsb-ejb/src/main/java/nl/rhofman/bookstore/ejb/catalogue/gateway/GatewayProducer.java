package nl.rhofman.bookstore.ejb.catalogue.gateway;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;
import nl.rhofman.bookstore.ejb.catalogue.gateway.jms.JmsCatalogueGateway;
import nl.rhofman.bookstore.ejb.xml.Catalog;

@Dependent
public class GatewayProducer {

    @Inject
    @Catalog
    private Queue queue;  // produced in JmsCatalogueConfiguration

    @Inject
    private JMSContext jmsContext; // produced in JmsConfiguration

    @Produces
    public CatalogueGateway createGateway() {
        return new JmsCatalogueGateway(queue, jmsContext);
    }

}
