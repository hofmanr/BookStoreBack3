package nl.rhofman.bookstore.ejb.catalogue.domain;

import nl.rhofman.bookstore.ejb.message.domain.DomainType;
import nl.rhofman.bookstore.ejb.message.domain.JmsDomainObject;

@DomainType(Catalogue.class)
public class JmsCatalogue extends JmsDomainObject<Catalogue> {
    public JmsCatalogue() {
        System.out.println("JmsCatalogue");
    }
}
