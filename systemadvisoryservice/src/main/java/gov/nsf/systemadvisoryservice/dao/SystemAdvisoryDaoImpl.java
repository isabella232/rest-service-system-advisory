/**
 * 
 */
package gov.nsf.systemadvisoryservice.dao;

import gov.nsf.systemadvisoryservice.common.exception.RollbackException;
import gov.nsf.systemadvisoryservice.common.util.Constants;
import gov.nsf.systemadvisoryservice.dao.rowmapper.SystemAdvisoryRowMapper;
import gov.nsf.systemadvisoryservice.model.SystemAdvisory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * Implementation of SystemAdvisoryDao interface
 * 
 * @author jlinden
 * 
 */
public class SystemAdvisoryDaoImpl implements SystemAdvisoryDao {
	@Autowired
	private DataSource dataSource;
	private NamedParameterJdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	private static final Logger LOGGER = Logger.getLogger(SystemAdvisoryDaoImpl.class);

	/**
	 * Returns system advisories from the database filtered by the specified
	 * applications passed
	 * 
	 * @return Collection of SystemAdvisories
	 * @throws RollbackException
	 */
	@Override
	public Collection<SystemAdvisory> getSystemAdvisories(List<String> applicationNames, String activeOnly) throws RollbackException {

		List<SystemAdvisory> sysAdvisoryList = new ArrayList<SystemAdvisory>();
		List<Integer> applicationIds = lookupApplicationIDs(applicationNames);
		try {
			RowMapper<SystemAdvisory> rowMapper = new SystemAdvisoryRowMapper();
			String queryStr = getActiveSystemAdvisoryQueryString(activeOnly);

			Map<String, List<Integer>> parameters = Collections.singletonMap(Constants.IDS_PARAMETER, applicationIds);

			sysAdvisoryList = this.jdbcTemplate.query(queryStr, parameters, rowMapper);
		} catch (Exception e) {
			LOGGER.error(Constants.ERROR_RETRIEVING_ACTIVE_ADVISORIES + e);
			throw new RollbackException(Constants.ERROR_RETRIEVING_ACTIVE_ADVISORIES);
		}

		return sysAdvisoryList;
	}

	/**
	 * Returns the system advisory from the DB with the specified ID.
	 * 
	 * @param advyID
	 * @return system advisory
	 */
	@Override
	public SystemAdvisory getSystemAdvisoryById(int advisoryId) throws RollbackException {
		SystemAdvisory systemAdvisory;
		Map<String, Integer> parameters = Collections.singletonMap(Constants.IDS_PARAMETER, advisoryId);

		try {
			List<SystemAdvisory> queryList = this.jdbcTemplate.query(Constants.GET_SYSTEM_ADVISORY_BY_ID_QUERY, parameters, new SystemAdvisoryRowMapper());
			if (!queryList.isEmpty()) {
				systemAdvisory = queryList.get(0);
			} else{
				systemAdvisory = null;
			}
		} catch (Exception e) {
			LOGGER.error(Constants.ERROR_RETRIVING_ADVISORY_BY_ID + e);
			throw new RollbackException(Constants.ERROR_RETRIVING_ADVISORY_BY_ID);
		}

		return systemAdvisory;
	}

	/**
	 * Inserts a new system advisory into the DB and returns the corresponding SystemAdvisory object.
	 * 
	 * @return SystemAdvisory
	 * @param systemAdvisory
	 * @throws RollbackException
	 */
	@Override
	public SystemAdvisory createSystemAdvisory(SystemAdvisory systemAdvisory) throws RollbackException {
		SystemAdvisory storedAdvisory = null;
		try {
			String queryStr = Constants.POST_SYSTEM_ADVISORY_QUERY;
			Map<String, String> parameters = getCreateSystemAdvisoryParametersMap(systemAdvisory);
			this.jdbcTemplate.update(queryStr, parameters);
			storedAdvisory = this.jdbcTemplate.query(Constants.RETRIVE_POST_QUERY, new SystemAdvisoryRowMapper()).get(0);
		} catch (Exception e) {
			LOGGER.error(Constants.ERROR_CREATING_NEW_ADVISORY + e);
			throw new RollbackException(Constants.ERROR_CREATING_NEW_ADVISORY);
		}

		return storedAdvisory;

	}

	/**
	 * Edits a system advisory in the data base an returns the corresponding SystemAdvisory object
	 * 
	 * @param SystemAdvisory
	 *            Object
	 * 
	 * 
	 * @return - SystemAdvisory
	 * @throws RollbackException
	 */

	@SuppressWarnings({ "rawtypes"})
	@Override
	public SystemAdvisory editSystemAdvisory(SystemAdvisory systemAdvisory) throws RollbackException {
		try {
			String queryStr = Constants.EDIT_SYSTEM_ADVISORY_QUERY;
			Map<String, Comparable> editParameters = getEditSystemAdvisoryParametersMap(systemAdvisory);
			this.jdbcTemplate.update(queryStr, editParameters);
		} catch (Exception e) {
			LOGGER.error(Constants.ERROR_EDITING_ADVISORY + e);
			throw new RollbackException(Constants.ERROR_EDITING_ADVISORY);
		}
		return systemAdvisory;
	}
	
	/**
	 * Archives a system advisory in the data base an returns the corresponding SystemAdvisory object
	 * 
	 * @param SystemAdvisory
	 *            Object
	 * 
	 * 
	 * @return - SystemAdvisory
	 * @throws RollbackException
	 */
	@SuppressWarnings({"rawtypes" })
	@Override
	public SystemAdvisory archiveSystemAdvisory(SystemAdvisory systemAdvisory) throws RollbackException {
		try {
			String queryStr = Constants.ARCHIVE_SYSTEM_ADVISORY_QUERY;
			Map<String, Comparable> archiveParameters = getArchiveSystemAdvisoryParametersMap(systemAdvisory);
			this.jdbcTemplate.update(queryStr, archiveParameters);
		} catch (Exception e) {
			LOGGER.error(Constants.ERROR_ARCHIVING_ADVISORY + e);
			throw new RollbackException(Constants.ERROR_ARCHIVING_ADVISORY);
		}

		return systemAdvisory;
	}

	/**
	 * Queries the application ID lookup table and returns a corresponding ID
	 * for every application name passed in the list.
	 * 
	 * @param applicationNames
	 *            - list of application name strings
	 * @return - list of integer IDs corresponding to their respective
	 *         application name
	 * @throws RollbackExceptionw
	 */
	private List<Integer> lookupApplicationIDs(List<String> applicationNames) throws RollbackException {
		List<Integer> ids = null;
		String query = applicationNames.isEmpty() ? Constants.LOOKUP_ALL_APP_IDS_QUERY : Constants.LOOKUP_APP_IDS_QUERY;
		Map<String, List<String>> namedParameters = Collections.singletonMap(Constants.NAMES_PARAMETER, applicationNames);
		try {
			ids = this.jdbcTemplate.queryForList(query, namedParameters, Integer.class);
		} catch (Exception e) {
			LOGGER.error(Constants.ERROR_QUERYING_APPLICATION_ID_LOOKUP_TABLE + e);
			throw new RollbackException(Constants.ERROR_QUERYING_APPLICATION_ID_LOOKUP_TABLE);
		}
		return ids;

	}
	
	/**
	 * Returns the appropriate query string for GET depending on the active parameter
	 * 
	 * @param activeOnly
	 * @return a query string
	 */
	private static String getActiveSystemAdvisoryQueryString(String activeOnly) {
		String queryStr;
		
		if(Constants.ACTIVE_TRUE.equalsIgnoreCase(activeOnly)){
			queryStr = Constants.GET_ACTIVE_SYSTEM_ADVISORIES_QUERY;
		}
		else if (Constants.ACTIVE_FALSE.equalsIgnoreCase(activeOnly)){
			queryStr = Constants.GET_INACTIVE_SYSTEM_ADVISORIES_QUERY;
		}
		else{
			queryStr = Constants.GET_ALL_SYSTEM_ADVISORIES_QUERY;
		}
		
		return queryStr;
	}

	/**
	 * Returns a parameter Map for creating a system advisory (See Constants for query statement)
	 * 
	 * @param systemAdvisory
	 * @return
	 */
	private static Map<String, String> getCreateSystemAdvisoryParametersMap(SystemAdvisory systemAdvisory) {

		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(Constants.APP_NAME, systemAdvisory.getAppName());
		parameters.put(Constants.ADVY_TITLE, systemAdvisory.getAdvisoryTitle());
		parameters.put(Constants.ADVY_TYPE, systemAdvisory.getAdvisoryType());
		parameters.put(Constants.ADVY_TXT, systemAdvisory.getAdvisoryText());
		parameters.put(Constants.ADVY_EFF_DATE, systemAdvisory.getAdvisoryEffectiveDate());
		parameters.put(Constants.ADVY_EXPY_DATE, systemAdvisory.getAdvisoryExpiryDate());
		parameters.put(Constants.LAST_UPDT_USER, systemAdvisory.getLastUpdateUser());
		parameters.put(Constants.LAST_UPDT_PGM, systemAdvisory.getLastUpdateProgram());
		return parameters;

	}

	/**
	 * Returns a parameter Map for editing a system advisory (See Constants for query statement)
	 * 
	 * @param systemAdvisory
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	private static Map<String, Comparable> getEditSystemAdvisoryParametersMap(SystemAdvisory systemAdvisory) {

		Map<String, Comparable> parameters = new HashMap<String, Comparable>();
		parameters.put(Constants.ADVY_ID, Integer.parseInt(systemAdvisory.getId()));
		parameters.put(Constants.ADVY_TYPE, systemAdvisory.getAdvisoryType());
		parameters.put(Constants.ADVY_TITLE, systemAdvisory.getAdvisoryTitle());
		parameters.put(Constants.ADVY_TXT, systemAdvisory.getAdvisoryText());
		parameters.put(Constants.ADVY_EFF_DATE, systemAdvisory.getAdvisoryEffectiveDate());
		parameters.put(Constants.ADVY_EXPY_DATE, systemAdvisory.getAdvisoryExpiryDate());
		parameters.put(Constants.ADVY_URL, systemAdvisory.getAdvisoryURL());
		parameters.put(Constants.LAST_UPDT_PGM, systemAdvisory.getLastUpdateProgram());
		parameters.put(Constants.LAST_UPDT_USER, systemAdvisory.getLastUpdateUser());

		return parameters;

	}
	
	/**
	 * Returns a parameter Map for archiving a system advisory (See Constants for query statement)
	 * 
	 * @param systemAdvisory
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	private static Map<String,Comparable> getArchiveSystemAdvisoryParametersMap(SystemAdvisory systemAdvisory) {

		Map<String,Comparable> parameters = new HashMap<String,Comparable>();
		parameters.put(Constants.ADVY_ID, Integer.parseInt(systemAdvisory.getId()));
		parameters.put(Constants.ADVY_EXPY_DATE, systemAdvisory.getAdvisoryExpiryDate());
		parameters.put(Constants.LAST_UPDT_PGM, systemAdvisory.getLastUpdateProgram());
		parameters.put(Constants.LAST_UPDT_USER, systemAdvisory.getLastUpdateUser());

		return parameters;

	}
	
	




}
