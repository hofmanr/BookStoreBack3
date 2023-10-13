package nl.rhofman.bookstore.ejb.catalogue.gateway.jms;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.jms.Queue;
import nl.rhofman.bookstore.ejb.xml.Catalog;

/**
 * Configuration for Catalogue. The beans can be used for CDI injection.
 *
 * @author R. Hofman
 */
@Dependent
public class JmsCatalogueConfiguration {
    @Produces
    @Catalog
    @Resource(name = "jms/catalogueOutputQE")
    private Queue catalogueOutputQueue;
}
