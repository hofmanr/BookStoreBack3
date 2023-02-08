package nl.rhofman.bookstore.ejb.catalogue.facade;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;

@MessageDriven(name = "CatalogueMessageListener",
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationType",
                        propertyValue = "javax.jms.Queue"),
                @ActivationConfigProperty(propertyName = "destination",
                        propertyValue = "jms/catalogueInputQE"),
                @ActivationConfigProperty(
                        propertyName = "connectionFactoryLookup",
                        propertyValue = "jms/bookstoreCF"
                )
        }
)
public class CatalogueMessageListenerBean implements MessageListener {

    public CatalogueMessageListenerBean() {
    }

    @Override
    public void onMessage(Message message) {

    }
}
