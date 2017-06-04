package igt.parsing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class WSDLParser {

	private String filepath;

	public WSDLParser(String filepath) {
		this.filepath = filepath;
	}

	public List<String> getServices() throws SAXException, IOException, ParserConfigurationException {
		Document doc = XMLParser.getNormalizedXMLDocument(this.filepath);
		NodeList nodes = doc.getDocumentElement().getChildNodes();
		List<String> results = new ArrayList<String>();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node currentNode = nodes.item(i);
			if (currentNode.getNodeType() == Node.ELEMENT_NODE && currentNode.getNodeName().equals("wsdl:service")) {
				NodeList childs = currentNode.getChildNodes();
				results.addAll(this.processPorts(childs));
			}
		}
		return results;
	}

	private List<String> processPorts(NodeList items) {
		List<String> ports = new ArrayList<String>();
		for (int i = 0; i < items.getLength(); i++) {
			Node currentChild = items.item(i);
			if (currentChild.getNodeType() == Node.ELEMENT_NODE && currentChild.getNodeName().contains("wsdl:port")) {
				String name = currentChild.getAttributes().getNamedItem("name").getTextContent();
				ports.add(name);
			}
		}
		return ports;
	}

}
