package nl.rhofman.bookstore.ejb.validate.repository;

import nl.rhofman.bookstore.ejb.validate.domain.ValidationResult;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Validator {
    private static final String ERRORCODE_PROPERTIES = "errorcodes.properties";
    private static final String ERRORMESSAGE_PROPERTIES = "errormessages.properties";

    private static Map<String, String> errorCodes = new ConcurrentHashMap<>();
    private static Map<String, String> errorMessages = new ConcurrentHashMap<>();

    protected String errorCode;
    protected String errorMessage;

    public Validator() {
        errorCode = getErrorCode();
        errorMessage = getErrorMessage();
    }

    public String errorMessage(String... values) {
        return MessageFormat.format(errorMessage, values);
    }

    public abstract ValidationResult validate();

    public String validationName() {
        return "default";
    }

    private String getErrorCode() {
        String code = errorCodes.get(validationName());
        if (code == null) {
            loadProperties(ERRORCODE_PROPERTIES);
            return errorCodes.get(validationName());
        }
        return code;
    }

    private String getErrorMessage() {
        String message = errorMessages.get(validationName());
        if (message == null) {
            loadProperties(ERRORMESSAGE_PROPERTIES);
            return errorMessages.get(validationName());
        }
        return message;
    }

    private void loadProperties(String filename) {
        try (InputStream input = Validator.class.getClassLoader().getResourceAsStream(filename)) {
            Properties properties = new Properties();

            // load a properties file
            properties.load(input);

            if (ERRORCODE_PROPERTIES.equals(filename)) {
                for (final String name: properties.stringPropertyNames())
                    errorCodes.put(name, properties.getProperty(name));
            } else {
                for (final String name: properties.stringPropertyNames())
                    errorMessages.put(name, properties.getProperty(name));
            }
        } catch (IOException ex) {
            return;
        }
    }
}
