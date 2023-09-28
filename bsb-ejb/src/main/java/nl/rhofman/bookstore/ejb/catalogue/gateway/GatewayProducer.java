package nl.rhofman.bookstore.ejb.catalogue.gateway;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.jms.JMSConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;
import nl.rhofman.bookstore.ejb.catalogue.gateway.jms.JmsCatalogueGateway;
import nl.rhofman.bookstore.ejb.xml.Catalog;
import nl.rhofman.bookstore.ejb.xml.service.AssemblerService;
import nl.rhofman.bookstore.ejb.xml.service.JaxbService;

@Dependent
public class GatewayProducer {

    @Resource(name = "jms/catalogueOutputQE")
    private Queue catalogueOutputQueue;

    @Inject
    @JMSConnectionFactory("java:comp/env/jms/bookstoreCF")
    private JMSContext jmsContext;

    @Inject
    @Catalog
    private JaxbService jaxbService;
    @Inject
    @Catalog
    private AssemblerService assemblerService;

    @Produces
    public CatalogueGateway createGateway() {
        return new JmsCatalogueGateway(catalogueOutputQueue, jmsContext, jaxbService, assemblerService);
    }

}
