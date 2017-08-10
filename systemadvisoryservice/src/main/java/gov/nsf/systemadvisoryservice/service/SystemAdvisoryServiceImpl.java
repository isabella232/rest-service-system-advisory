package gov.nsf.systemadvisoryservice.service;

import gov.nsf.systemadvisoryservice.common.exception.RollbackException;
import gov.nsf.systemadvisoryservice.dao.SystemAdvisoryDao;
import gov.nsf.systemadvisoryservice.model.SystemAdvisory;
import gov.nsf.systemadvisoryservice.model.SystemAdvisoryWrapper;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the SystemAdvisoryService interface
 * 
 * @author jlinden
 * 
 */
public class SystemAdvisoryServiceImpl implements SystemAdvisoryService {
	@Autowired
	private SystemAdvisoryDao systemAdvisoryDao;

	public void setSystemAdvisoryDao(SystemAdvisoryDao systemAdvisoryDao) {
		this.systemAdvisoryDao = systemAdvisoryDao;
	}

	/**
	 * Returns requested system advisories
	 * 
	 * @param applicationNames
	 * @param activeOnly
	 * 
	 * @return - Collection of System Advisory objects
	 * @throws RollbackException
	 */
	@Transactional(value = "flp", readOnly = true, propagation = Propagation.REQUIRES_NEW, rollbackFor = { RuntimeException.class, RollbackException.class })
	@Override
	public Collection<SystemAdvisory> getSystemAdvisories(List<String> applicationNames, String activeOnly) throws RollbackException {
		
		Collection<SystemAdvisory> sysAdvisoryList = systemAdvisoryDao.getSystemAdvisories(applicationNames, activeOnly);
		return sysAdvisoryList;
	}

	/**
	 * Creates a new system advisory and returns the newly stored advisory in a wrapper
	 * 
	 * @param systemAdvisory
	 * @throws RollbackException
	 */
	@Transactional(value = "flp", readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = { RuntimeException.class, RollbackException.class })
	@Override
	public SystemAdvisoryWrapper createSystemAdvisory(SystemAdvisory systemAdvisory) throws RollbackException {
		
		SystemAdvisory advisory = systemAdvisoryDao.createSystemAdvisory(systemAdvisory);
		SystemAdvisoryWrapper wrapper = new SystemAdvisoryWrapper(advisory);
		return wrapper;
	}

	/**
	 * Edit a system advisory and returns the updated stored advisory in a wrapper
	 * 
	 * @param systemAdvisory
	 * @throws RollbackException
	 */
	@Transactional(value = "flp", readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = { RuntimeException.class, RollbackException.class })	
	@Override
	public SystemAdvisoryWrapper editSystemAdvisory(SystemAdvisory systemAdvisory) throws RollbackException {

		SystemAdvisory advisory = systemAdvisoryDao.editSystemAdvisory(systemAdvisory);
		SystemAdvisoryWrapper wrapper = new SystemAdvisoryWrapper(advisory);
		return wrapper;
	}

	/**
	 * Archive a system advisory and returns the archived advisory in a wrapper
	 * 
	 * @param systemAdvisory
	 * @throws RollbackException
	 */
	@Transactional(value = "flp", readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = { RuntimeException.class, RollbackException.class })	
	@Override
	public SystemAdvisoryWrapper archiveSystemAdvisory(SystemAdvisory systemAdvisory) throws RollbackException {
		SystemAdvisory advisory = systemAdvisoryDao.archiveSystemAdvisory(systemAdvisory);
		SystemAdvisoryWrapper wrapper = new SystemAdvisoryWrapper(advisory);
		return wrapper;
	}

}
