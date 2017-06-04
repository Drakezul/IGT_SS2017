package application;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.rmi.CORBA.Util;
import javax.swing.plaf.synth.SynthSpinnerUI;

import model.ServiceProposal;
import util.StringUtil;

public class Analyzer {

	public static List<ServiceProposal> getMatchingServices(String activityName, List<String> serviceNames,
			int minMatchingDegree) {
		List<ServiceProposal> matchingServiceProposals = new ArrayList<ServiceProposal>();
		for (String serviceName : serviceNames) {
			double similarity = StringUtil.calculateStringmatching(activityName, serviceName);

			if ((similarity*100) > minMatchingDegree) {
				ServiceProposal recommandation = new ServiceProposal(serviceName, similarity);
				matchingServiceProposals.add(recommandation);
			}
		}
		return matchingServiceProposals;
	}
}