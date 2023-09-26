package nl.rhofman.bookstore.ejb.catalogue.dao;

import jakarta.annotation.Resource;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSConnectionFactory;
import jakarta.jms.Queue;
import nl.rhofman.bookstore.ejb.catalogue.dao.jms.JmsCatalogueGateway;
import nl.rhofman.bookstore.ejb.xml.Catalog;
import nl.rhofman.bookstore.ejb.xml.service.AssemblerService;
import nl.rhofman.bookstore.ejb.xml.service.JaxbService;

public class GatewayProducer {

    @Resource(name = "jms/catalogueOutputQE")
    private Queue catalogueOutputQueue;

    @Inject
    @JMSConnectionFactory("java:comp/env/jms/bookstoreCF")
    private ConnectionFactory connectionFactory;

    @Inject
    @Catalog
    private JaxbService jaxbService;
    @Inject
    @Catalog
    private AssemblerService assemblerService;

    @Produces
    public CatalogueGateway createGateway() {
        return new JmsCatalogueGateway(catalogueOutputQueue, connectionFactory, jaxbService, assemblerService);
    }

}
