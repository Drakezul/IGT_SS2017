package mapping;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.rmi.CORBA.Util;

import Util.StringUtil;
import model.ServiceProposal;

public class Analyzer {

	public List<ServiceProposal> getMatchingServices(String activityName, List<String> serviceNames,
			double minMatchingDegree) {
		List<ServiceProposal> matchingServiceProposals = new ArrayList<ServiceProposal>();
		for (String serviceName : serviceNames) {
			double similarity = StringUtil.calculateStringmatching(activityName, serviceName);

			if (minMatchingDegree > 0.25) {
				ServiceProposal recommandation = new ServiceProposal(serviceName, similarity);
				matchingServiceProposals.add(recommandation);
			}
		}
		/*matchingServiceProposals.sort(new Comparator<ServiceProposal>() {
			@Override
			public int compare(ServiceProposal o1, ServiceProposal o2) {
				return Double.compare(o2.matchingDegree, o1.matchingDegree);
			}
		});*/
		return matchingServiceProposals;
	}
}