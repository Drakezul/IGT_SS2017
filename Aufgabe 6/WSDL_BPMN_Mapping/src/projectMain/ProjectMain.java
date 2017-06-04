package projectMain;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import ui.UiCore;

public class ProjectMain {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		String wsdlPath = "./resources/StockQuote.wsdl";
		String bpmnPath = "./resources/CCBPMN.bpmn";
		String imagePath = "./resources/CCBPMN.png";
		new UiCore(wsdlPath, bpmnPath, imagePath);
	}
}
