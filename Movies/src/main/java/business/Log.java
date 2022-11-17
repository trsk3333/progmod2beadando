/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Tulajdonos
 */
public class Log<T> {
    public void handleException(Exception ex) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dbf.newDocumentBuilder();
            File file = new File("log.xml");
            Document xml = builder.parse(file);
            xml.normalize();
            Element root = xml.getDocumentElement();
            Element exe = xml.createElement("exception");
            Element message = xml.createElement("message");
            message.setTextContent(ex.getMessage());
            Element exClass = xml.createElement("class");
            exClass.setTextContent(ex.getClass().toString());
            Element stackTrace = xml.createElement("stackTrace");
            stackTrace.setTextContent(Arrays.toString(ex.getStackTrace()));
            Element datetime = xml.createElement("datetime");
            datetime.setTextContent(new Date().toString());
            exe.appendChild(exClass);
            exe.appendChild(message);
            exe.appendChild(stackTrace);
            root.appendChild(exe);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(xml);
            StreamResult result = new StreamResult(file);
            transformer.transform(source, result);

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
