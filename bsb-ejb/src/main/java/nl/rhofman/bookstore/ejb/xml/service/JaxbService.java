package nl.rhofman.bookstore.ejb.xml.service;

import jakarta.xml.bind.*;
import jakarta.xml.bind.util.JAXBSource;

import javax.xml.XMLConstants;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public abstract class JaxbService {

    protected abstract String getJaxbPackage(String messageType);

    protected abstract Map<String, JAXBContext> unmarshallContexts();
    protected abstract Map<String, JAXBContext> marshallContexts();


    public Object unmarshall(String messageType, String xml) {
        try {
            JAXBContext jaxbContext = unmarshallContexts().get(messageType);
            if (jaxbContext == null) {
                jaxbContext = JAXBContext.newInstance(getJaxbPackage(messageType));
                unmarshallContexts().put(messageType, jaxbContext);
            }
            // The root element must be an anonymous type (in the XSD). If not the unmarshaller returns a JAXBElement:
            // If the root element uniquely corresponds to a Java class then an instance of that class will be returned, and if not a JAXBElement will be returned.
            // see https://stackoverflow.com/questions/10243679/when-does-jaxb-unmarshaller-unmarshal-returns-a-jaxbelementmyschemaobject-or-a
            InputStream xmlStream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            JAXBIntrospector jaxbIntrospector = jaxbContext.createJAXBIntrospector();
            Object jaxbElement = unmarshaller.unmarshal(xmlStream);
            return jaxbIntrospector.getValue(jaxbElement);
         } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String marshall(Object jaxbObject) {
        try {
            String className = jaxbObject.getClass().getSimpleName();
            JAXBContext jaxbContext = marshallContexts().get(className);
            if (jaxbContext == null) {
                jaxbContext = JAXBContext.newInstance(jaxbObject.getClass());
                marshallContexts().put(className, jaxbContext);
            }
            // Alternative:
//            Marshaller marshaller = jaxbContext.createMarshaller();
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//            StringWriter writer = new StringWriter();
//            marshaller.marshal(jaxbObject, writer);
//            return writer.toString();

            JAXBSource source = new JAXBSource(jaxbContext, jaxbObject);

            TransformerFactory transformerFactory = getTransformerFactory();
            Transformer xsltTransformer = getTransformer(transformerFactory);

            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);

            xsltTransformer.transform(source, result);
            return writer.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }

    private TransformerFactory getTransformerFactory() throws TransformerConfigurationException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        return transformerFactory;
    }

    private Transformer getTransformer(TransformerFactory factory) throws TransformerConfigurationException {
        Source xslt = new StreamSource(JaxbService.class.getClassLoader().getResourceAsStream("normalize.xslt"));

        Transformer transformer = factory.newTransformer(xslt);
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        return transformer;
    }

}
