package nl.rhofman.bookstore.ejb.xml;

import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import javax.xml.XMLConstants;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.*;
import java.io.StringReader;
import java.io.StringWriter;

public class XmlUtil {

    private XmlUtil() {
        // prevent instantiation
    }

    public static String printXML(String xmlString) {
        try {
            StreamSource xmlInSource = new StreamSource(new StringReader(xmlString));

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            Transformer tf = transformerFactory.newTransformer();

            tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            tf.setOutputProperty(OutputKeys.METHOD, "xml");
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            StringWriter writer = new StringWriter();
            tf.transform(xmlInSource, new StreamResult(writer));
            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String normalizeXml(String xmlString) {
        Source xslt = new StreamSource(XmlUtil.class.getClassLoader().getResourceAsStream("normalize.xslt"));
        StreamSource xmlSource = new StreamSource(new StringReader(xmlString));

        TransformerFactory factory = TransformerFactory.newInstance();
        try {
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            Transformer transformer = factory.newTransformer(xslt);
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");

            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);

            transformer.transform(xmlSource, result);
            return writer.toString();

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getRootElementName(String xml) {
        InputSource inputXML = new InputSource(new StringReader(xml));
        XPath xPath = XPathFactory.newInstance().newXPath();
        try {
            XPathExpression expr = xPath.compile("/*");
            Node node = (Node) expr.evaluate(inputXML, XPathConstants.NODE);
            if (node != null) {
                return node.getLocalName();
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
