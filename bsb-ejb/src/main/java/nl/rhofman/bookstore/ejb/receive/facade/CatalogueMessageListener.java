package nl.rhofman.bookstore.ejb.receive.facade;

import jakarta.ejb.MessageDriven;
import jakarta.inject.Inject;
import jakarta.jms.Message;
import nl.rhofman.bookstore.ejb.receive.service.CatalogueMessageService;
import nl.rhofman.bookstore.ejb.jms.AbstractJmsListener;
import nl.rhofman.exception.dao.ServiceException;

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
        try {
            String payload = getPayload(message);
            messageService.processMessageReceived(payload);
        } catch (ServiceException ex) {
            System.out.println("Service exception with" +
                    "\n   > origin : " + ex.getOrigin().getCode() +
                    "\n   > reason : " + ex.getReason().getCode()  +
                    "\n   > cause  : " + ex.getCause().getMessage() +
                    "\n   > message: " + ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
