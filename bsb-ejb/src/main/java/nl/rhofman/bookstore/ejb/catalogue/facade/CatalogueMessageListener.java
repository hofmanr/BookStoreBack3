package nl.rhofman.bookstore.ejb.catalogue.facade;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;

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
public class CatalogueMessageListener implements MessageListener {

    public CatalogueMessageListener() {
    }

    @Override
    public void onMessage(Message message) {
        System.out.println("Received a message!");

    }
}
