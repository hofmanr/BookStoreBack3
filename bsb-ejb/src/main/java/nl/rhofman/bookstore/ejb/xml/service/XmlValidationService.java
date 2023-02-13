package nl.rhofman.bookstore.ejb.xml.service;

import nl.rhofman.exception.dao.ServiceException;
import nl.rhofman.exception.domain.ExceptionOrigin;
import nl.rhofman.exception.domain.ExceptionReason;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.Arrays;

public abstract class XmlValidationService {
    protected static final ExceptionReason XML_EXCEPTION_REASON = new ExceptionReason("XER", "XML validation failed");
    protected static final ExceptionOrigin EXCEPTION_ORIGIN =
            new ExceptionOrigin("VAL", "Validation");

    private Validator validator;

    public void validateXml(String xml) {
        try {
            if (validator != null)
                validator.validate(new StreamSource(new StringReader(xml)));
        } catch (IOException | SAXException e) {
            throw new ServiceException(EXCEPTION_ORIGIN, XML_EXCEPTION_REASON, e.getMessage(), e);
        }
    }

    public void setHandler(ErrorHandler errorHandler) {
        validator.setErrorHandler(errorHandler);
    }

    public void validatePayload(Source payload) {
        try {
            validator.validate(payload);
        } catch (SAXException | IOException e) {
            throw new ServiceException(EXCEPTION_ORIGIN, XML_EXCEPTION_REASON, "Error validating payload", e);
        }
    }

    protected void loadSchemas(String xsdDirectory, String... xsdFiles) {
        SchemaFactory schemaFactory =
                SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source[] sources = Arrays.stream(xsdFiles)
                .map(f -> String.format("%s/%s", xsdDirectory, f))
                .map(this::readFile)
                .map(StreamSource::new)
                .toArray(Source[]::new);

        try {
//            schemaFactory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
//            schemaFactory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            // One file:
            // return schemaFactory.newSchema(new File("purchaseOrder.xsd"));
            Schema schema = schemaFactory.newSchema(sources);
            validator = schema.newValidator();
//            validator.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
//            validator.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        } catch (SAXException e) {
            throw new ServiceException(EXCEPTION_ORIGIN, XML_EXCEPTION_REASON, "Error creating new schema", e);
        }
    }

    protected InputStream readFile(String fileName) {
        try {
            URL url = this.getClass().getResource(fileName);
            return url.openStream();
        } catch (IOException e) {
            throw new ServiceException(EXCEPTION_ORIGIN, XML_EXCEPTION_REASON, "Error get resource " + fileName, e);
        }
    }
}
