package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;

import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import application.Analyzer;
import model.ServiceProposal;
import model.StockQuote_Testdata;
import parsing.BPMNParser;
import parsing.WSDLParser;

public class UiCore extends JFrame {

	private static final long serialVersionUID = -2406163634603059336L;

	private String bpmnImagePath;

	private JSpinner inputField;
	private JPanel serviceDisplayPanel;
	private final StockQuote_Testdata testdata = new StockQuote_Testdata();

	public UiCore(String wsdlFilePath, String bpmnFilePath, String bpmnImagePath) {
		super("IGT BPMN to WSDL Mapping");
		this.bpmnImagePath = bpmnImagePath;

		File bpmnFile = new File(bpmnFilePath);
		File wsdlFile = new File(wsdlFilePath);
		// File testFile = new File(testFilePath);

		WSDLParser wsdlService = new WSDLParser(wsdlFile.getAbsolutePath());
		BPMNParser bpmnParser = new BPMNParser(bpmnFile.getAbsolutePath());

		this.setLayout(new BorderLayout(5, 5));

		try {
			List<String> services = wsdlService.getServices();
			List<String> activities = bpmnParser.getBPMNActivities();

			add(this.createCenterPanel(), BorderLayout.CENTER);
			add(this.createEastPanel(activities, services), BorderLayout.EAST);

			// this.initializeServicesPanel();

		} catch (SAXException | IOException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		// scale window
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle bounds = env.getMaximumWindowBounds();
		this.setPreferredSize(new Dimension((int) bounds.getWidth(), (int) bounds.getHeight()));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.pack();
	}

	private JScrollPane createCenterPanel() {
		return new JScrollPane(new ImagePanel(this.bpmnImagePath));
	}

	private JPanel createEastPanel(List<String> activities, List<String> serviceNames) {
		JPanel eastPanel = new JPanel(new BorderLayout());
		// add inputField

		eastPanel.add(this.createMatchingDegreePanel(), BorderLayout.NORTH);
		// add activityButtons for activity selection
		eastPanel.add(this.createActivityButtons(activities, serviceNames), BorderLayout.CENTER);

		eastPanel.add(createServiceDisplay(), BorderLayout.SOUTH);

		return eastPanel;
	}

	private JPanel createMatchingDegreePanel() {
		this.inputField = this.createMatchingDegreeInputField();

		JLabel degreeLabel = new JLabel("Matching Degree: ");
		degreeLabel.setLabelFor(this.inputField);

		JLabel percent = new JLabel("%");
		percent.setLabelFor(inputField);

		JPanel matchingDegreePanel = new JPanel(new FlowLayout());
		matchingDegreePanel.add(degreeLabel);
		matchingDegreePanel.add(this.inputField);
		matchingDegreePanel.add(percent);
		return matchingDegreePanel;

	}

	private JSpinner createMatchingDegreeInputField() {
		SpinnerModel model = new SpinnerNumberModel(0, 0, 100, 1);
		return new JSpinner(model);
	}

	private JScrollPane createActivityButtons(List<String> activities, List<String> serviceNames) {
		JPanel scrollablePanel = new JPanel();
		scrollablePanel.setLayout(new BoxLayout(scrollablePanel, BoxLayout.Y_AXIS));
		for (String activity : activities) {
			JButton activityButton = new JButton(activity);

			activityButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					List<ServiceProposal> mappingResults = Analyzer.getMatchingServices(activity, serviceNames,
							(int) inputField.getValue());
					serviceDisplayPanel.removeAll();
					mappingResults.sort(new Comparator<ServiceProposal>() {
						@Override
						public int compare(ServiceProposal o1, ServiceProposal o2) {
							return Double.compare(o2.matchingDegree, o1.matchingDegree);
						}
					});
					fillServiceDisplay(activity, mappingResults);
					populatePrecisionAndRecallPanel(activity, mappingResults);
				}
			});
			scrollablePanel.add(activityButton);
		}
		return new JScrollPane(scrollablePanel);
	}

	private JScrollPane createServiceDisplay() {
		this.serviceDisplayPanel = new JPanel(new BorderLayout());
		return new JScrollPane(this.serviceDisplayPanel);
	}

	private void fillServiceDisplay(String activityName, List<ServiceProposal> serviceProposals) {
		JPanel wrapperPanel = new JPanel();
		wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.X_AXIS));
		// service panel
		JPanel servicePanel = new JPanel(new GridLayout(0, 1));
		wrapperPanel.add(servicePanel);
		servicePanel.add(new JLabel(activityName));
		servicePanel.add(new JSeparator(SwingConstants.HORIZONTAL));
		// matching degree panel
		JPanel matchinDegreePanel = new JPanel(new GridLayout(0, 1));
		wrapperPanel.add(matchinDegreePanel);
		matchinDegreePanel.add(new JLabel(""));
		matchinDegreePanel.add(new JSeparator(SwingConstants.HORIZONTAL));
		// add labels
		for (ServiceProposal proposal : serviceProposals) {
			servicePanel.add(new JLabel(proposal.serviceName + ": "));
			matchinDegreePanel.add(new JLabel(doubleTo2DecimalPlaces(proposal.matchingDegree * 100) + "%"));
		}
		this.serviceDisplayPanel.add(wrapperPanel, BorderLayout.CENTER);

		this.validate();
	}
	
	private DecimalFormat twoDecimals = new DecimalFormat("0.00");  
	
	private String doubleTo2DecimalPlaces(double value){
		return twoDecimals.format(value);
	}

	private void populatePrecisionAndRecallPanel(String activityName, List<ServiceProposal> serviceProposals) {
		JPanel precisionAndRecallPanel = new JPanel(new GridLayout(0, 1));
		List<String> validServices = this.testdata.map.get(activityName);
		
		int relevantServicesFound = 0;
		for (ServiceProposal proposedService : serviceProposals) {
			if (validServices != null && validServices.contains(proposedService.serviceName)) {
				relevantServicesFound++;
			}
		}
		// share of valid services of the proposed services
		double precision = 0.0;
		if (serviceProposals.size() > 0) {
			precision = (double) relevantServicesFound / serviceProposals.size() * 100;
		}
		// share of the valid services actually found
		double recall = 0.0;
		if (validServices != null) {
			recall = (double) relevantServicesFound / validServices.size() * 100;
		}
		// F-Measure
		double fMeasure = 0.0;
		double fSum = precision + recall;
		if (fSum != 0.0) {
			fMeasure = (2 * precision * recall) / fSum;
		}
		precisionAndRecallPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
		precisionAndRecallPanel.add(new JLabel("Precision: " + doubleTo2DecimalPlaces(precision)));
		precisionAndRecallPanel.add(new JLabel("Recall: " + doubleTo2DecimalPlaces(recall)));
		precisionAndRecallPanel.add(new JLabel("F-Measure: " + doubleTo2DecimalPlaces(fMeasure)));
		this.serviceDisplayPanel.add(precisionAndRecallPanel, BorderLayout.SOUTH);
		this.validate();
	}
}
