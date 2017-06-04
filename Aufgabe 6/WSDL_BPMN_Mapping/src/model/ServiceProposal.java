package model;

public class ServiceProposal {
	
	public String serviceName;
	public double matchingDegree;

	public ServiceProposal(String serviceName, double matchingValue) {
		super();
		this.serviceName = serviceName;
		this.matchingDegree = matchingValue;
	}

}
