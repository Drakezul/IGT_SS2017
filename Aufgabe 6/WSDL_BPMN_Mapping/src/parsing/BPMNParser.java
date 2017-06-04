package parsing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class BPMNParser {

	private String filepath;

	public BPMNParser(String filepath) {
		this.filepath = filepath;
	}

	public List<String> getBPMNActivities() throws SAXException, IOException, ParserConfigurationException {
		Document doc = XMLParser.getNormalizedXMLDocument(this.filepath);
		NodeList nodes = doc.getElementsByTagName("task");

		List<String> results = new ArrayList<String>();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node currentNode = nodes.item(i);
			if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
				results.add(currentNode.getAttributes().getNamedItem("name").getTextContent());
			}
		}
		return results;
	}

}
