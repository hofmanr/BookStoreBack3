package nl.rhofman.bookstore.ejb.catalogue.facade;

import jakarta.ejb.MessageDriven;
import jakarta.inject.Inject;
import jakarta.jms.Message;
import nl.rhofman.bookstore.ejb.catalogue.service.CatalogueMessageService;
import nl.rhofman.bookstore.ejb.jms.AbstractJmsListener;

//@MessageDriven(name = "CatalogueMessageListener",
//        activationConfig = {
//                @ActivationConfigProperty(propertyName = "destinationType",
//                        propertyValue = "jakarta.jms.Queue"),
//                @ActivationConfigProperty(propertyName = "destination",
//                        propertyValue = "jms/catalogueInputQE"),
//                @ActivationConfigProperty(
//                        propertyName = "connectionFactoryLookup",
//                        propertyValue = "jms/bookstoreCF"
//                )
//        }
//)
@MessageDriven
public class CatalogueMessageListener extends AbstractJmsListener {

    @Inject
    private CatalogueMessageService messageService;

    public CatalogueMessageListener() {
    }

    @Override
    public void onMessage(Message message) {
        String payload = getPayload(message);
        System.out.println("Received message " + payload);
        messageService.processMessageReceived(payload);
    }
}
