package gov.nsf.systemadvisoryservice.dao;

import gov.nsf.systemadvisoryservice.common.exception.RollbackException;
import gov.nsf.systemadvisoryservice.model.SystemAdvisory;

import java.util.Collection;
import java.util.List;

/**
 * 
 * Interface for SystemAdvisoryDao 
 * 
 * @author jlinden
 *
 */
public interface SystemAdvisoryDao {
	
	public Collection<SystemAdvisory> getSystemAdvisories(List<String> applicationNames, String activeOnly) throws RollbackException;
	public SystemAdvisory createSystemAdvisory(SystemAdvisory systemAdvisory) throws RollbackException;
	public SystemAdvisory getSystemAdvisoryById(int id) throws RollbackException;
	public SystemAdvisory editSystemAdvisory(SystemAdvisory systemAdvisory) throws RollbackException;
	public SystemAdvisory archiveSystemAdvisory(SystemAdvisory systemAdvisory) throws RollbackException;

}
