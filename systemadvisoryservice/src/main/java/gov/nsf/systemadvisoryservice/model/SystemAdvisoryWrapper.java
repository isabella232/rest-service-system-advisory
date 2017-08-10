package gov.nsf.systemadvisoryservice.model;

import java.util.ArrayList;
import java.util.List;

/**
 * System Advisory response wrapper - extends the BaseResponseWrapper
 * 
 * @author jlinden
 *
 */
public class SystemAdvisoryWrapper extends BaseResponseWrapper{
	private List<SystemAdvisory> systemAdvisories;
	
	public SystemAdvisoryWrapper(List<SystemAdvisory> systemAdvisories){
		this.setSystemAdvisories(systemAdvisories);
	}
	
	public SystemAdvisoryWrapper(){
		this.setSystemAdvisories(new ArrayList<SystemAdvisory>());
	}

	public SystemAdvisoryWrapper(SystemAdvisory systemAdvisory) {
		this();
		this.systemAdvisories.add(systemAdvisory);
	}

	public List<SystemAdvisory> getSystemAdvisories() {
		return systemAdvisories;
	}

	public void setSystemAdvisories(List<SystemAdvisory> systemAdvisories) {
		this.systemAdvisories = systemAdvisories;
	}
	
	public void editSystemAdvisory(List<SystemAdvisory> systemAdvisories)
	{
		this.systemAdvisories = systemAdvisories;
	}
	
	@Override
	public String toString(){
		StringBuilder systemAdvisoryWrapperDetails = new StringBuilder();
		systemAdvisoryWrapperDetails.append("System Advisories : ");
		systemAdvisoryWrapperDetails.append(this.getSystemAdvisories());
		systemAdvisoryWrapperDetails.append("\n");
		systemAdvisoryWrapperDetails.append(super.toString());

		return systemAdvisoryWrapperDetails.toString();
	}
}
