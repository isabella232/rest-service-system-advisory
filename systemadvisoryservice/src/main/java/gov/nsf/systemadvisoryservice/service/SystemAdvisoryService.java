package gov.nsf.systemadvisoryservice.service;



import gov.nsf.systemadvisoryservice.common.exception.RollbackException;
import gov.nsf.systemadvisoryservice.model.SystemAdvisory;
import gov.nsf.systemadvisoryservice.model.SystemAdvisoryWrapper;

import java.util.Collection;
import java.util.List;

/**
 * Interface for the SystemAdvisoryService
 * @author jlinden
 *
 */
public interface SystemAdvisoryService {

	public Collection<SystemAdvisory> getSystemAdvisories(List<String> applicationNames, String activeOnly) throws RollbackException;
	public SystemAdvisoryWrapper createSystemAdvisory(SystemAdvisory systemAdvisory) throws RollbackException;
	public SystemAdvisoryWrapper editSystemAdvisory(SystemAdvisory systemAdvisory) throws RollbackException;
	public SystemAdvisoryWrapper archiveSystemAdvisory(SystemAdvisory systemAdvisory) throws RollbackException;

}
