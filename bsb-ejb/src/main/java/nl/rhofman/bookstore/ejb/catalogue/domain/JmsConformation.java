package nl.rhofman.bookstore.ejb.catalogue.domain;

import nl.rhofman.bookstore.ejb.message.domain.DomainType;
import nl.rhofman.bookstore.ejb.message.domain.JmsDomainObject;

@DomainType(Confirmation.class)
public class JmsConformation extends JmsDomainObject<Confirmation> {
    public JmsConformation() {
        System.out.println("JmsConformation");
    }
}
