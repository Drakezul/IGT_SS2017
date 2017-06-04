package igt.parsing;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XMLParser {

	public static Document getNormalizedXMLDocument(String filepath) throws SAXException, IOException, ParserConfigurationException {
		File file = new File(filepath);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document doc = factory.newDocumentBuilder().parse(file); 
		doc.getDocumentElement().normalize();
		return doc;
	}

}
