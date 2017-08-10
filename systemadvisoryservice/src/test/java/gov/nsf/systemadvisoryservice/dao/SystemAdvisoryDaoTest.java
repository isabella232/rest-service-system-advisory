package gov.nsf.systemadvisoryservice.dao;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import gov.nsf.systemadvisoryservice.common.TestUtils;
import gov.nsf.systemadvisoryservice.common.exception.RollbackException;
import gov.nsf.systemadvisoryservice.common.util.Constants;
import gov.nsf.systemadvisoryservice.dao.rowmapper.SystemAdvisoryRowMapper;
import gov.nsf.systemadvisoryservice.model.SystemAdvisory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * Unit tests for SystemAdvisoryDaoImpl
 * 
 * @author jlinden
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class SystemAdvisoryDaoTest {

	@InjectMocks
	private SystemAdvisoryDaoImpl systemAdvisoryDao;

	@Mock
	private NamedParameterJdbcTemplate jdbcTemplateMock;

	private static final Logger logger = Logger.getLogger(SystemAdvisoryDaoTest.class);

	/**
	 * Tests that the normal behavior of the getSystemAdvisories method properly
	 * returns a Collection of SystemAdvisory objects from the database
	 * 
	 * Mocks the JdbcTemplate (holding the DB connection) to return the
	 * Collection of system advisories Asserts that a non-null list with the
	 * expected size is returned
	 * 
	 * @throws RollbackException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void getSystemAdvisoriesHappyPathTest() throws RollbackException {

		ArrayList<SystemAdvisory> mockedAdvisories = TestUtils.getMockAdvisories(1, 2, 3);
		ArrayList<String> applicationNames = new ArrayList<String>();
		applicationNames.add("MyNSF");
		when(jdbcTemplateMock.query(any(String.class), any(Map.class), any(SystemAdvisoryRowMapper.class))).thenReturn(mockedAdvisories);

		Collection<SystemAdvisory> sysAdvisories = systemAdvisoryDao.getSystemAdvisories(applicationNames, "true");

		assertNotNull(sysAdvisories);
		assertEquals(sysAdvisories.size(), mockedAdvisories.size());
		logger.info(" Active System Advisories\n" + sysAdvisories + "\n End of Advisories");

	}

	/**
	 * Tests that the getSystemAdvisories method properly handles exceptions
	 * thrown from the database
	 * 
	 * Mocks the JdbcTemplate (holding the DB connection) to throw an Exception
	 * Test requires that the method throws a RollbackException to pass
	 * 
	 * @throws RollbackException
	 */
	@SuppressWarnings("unchecked")
	@Test(expected = RollbackException.class)
	public void getSystemAdvisoriesExceptionsTest() throws RollbackException {
		ArrayList<String> applicationNames = new ArrayList<String>();
		applicationNames.add("MyNSF");
		
		when(jdbcTemplateMock.query(any(String.class), any(Map.class), any(SystemAdvisoryRowMapper.class))).thenThrow(new DataAccessResourceFailureException("An exception was thrown from the DB"));
		systemAdvisoryDao.getSystemAdvisories(applicationNames, "true");

	}

	/**
	 * Tests that the getSystemAdvisories method properly handles exceptions
	 * thrown by the queryForList statement in the private method
	 * lookupApplicationIDs
	 * 
	 * Mocks the JdbcTemplate (holding the DB connection) to throw an Exception
	 * Test requires that the method throws a ResourceNotFoundException to pass
	 * 
	 * @throws RollbackException
	 */
	@Test(expected = RollbackException.class)
	public void getSystemAdvisoriesLookUpExceptionsTest() throws RollbackException {
		ArrayList<String> applicationNames = new ArrayList<String>();
		applicationNames.add("MyNSF");
		applicationNames.add("eJacket");

		Map<String, ArrayList<String>> parameters = Collections.singletonMap("names", applicationNames);
		when(jdbcTemplateMock.queryForList(Constants.LOOKUP_APP_IDS_QUERY, parameters, Integer.class)).thenThrow(new DataAccessResourceFailureException("An exception was thrown during the application ID lookup query."));
		systemAdvisoryDao.getSystemAdvisories(applicationNames,"true");

	}

	/**
	 * Tests that the normal behavior of the createSystemAdvisory method
	 * properly inserts into the DB
	 * 
	 * Mocks the JdbcTemplate (holding the DB connection) to successfully
	 * "insert" into the DB
	 * 
	 * @throws RollbackException
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void createSystemAdvisoryHappyPathTest() throws RollbackException {

		SystemAdvisory systemAdvisory = TestUtils.getMockAdvisories(1).get(0);
		when(jdbcTemplateMock.update(any(String.class), any(Map.class))).thenReturn(1);
		when(jdbcTemplateMock.query(any(String.class), any(SystemAdvisoryRowMapper.class))).thenReturn(TestUtils.getMockAdvisories(1));
		SystemAdvisory returnedAdvisory = systemAdvisoryDao.createSystemAdvisory(systemAdvisory);
		assertNotNull(returnedAdvisory);
	}

	/**
	 * Tests that the createSystemAdvisory method properly handles exceptions
	 * thrown from the database
	 * 
	 * Mocks the JdbcTemplate (holding the DB connection) to throw an Exception
	 * Test requires that the method throws a RollbackException to pass
	 * 
	 * @throws RollbackException
	 */
	@SuppressWarnings("unchecked")
	@Test(expected = RollbackException.class)
	public void createSystemAdvisoryExceptionsTest() throws RollbackException {
		SystemAdvisory systemAdvisory = TestUtils.getMockAdvisories(1).get(0);
		when(jdbcTemplateMock.update(any(String.class), any(Map.class))).thenThrow(new DataAccessResourceFailureException("An exception was thrown when inserting an advisory in the DB."));
		systemAdvisoryDao.createSystemAdvisory(systemAdvisory);
	}

	/**
	 * Tests that the normal behavior of the editSystemAdvisory method properly
	 * update into the DB
	 * 
	 * Mocks the JdbcTemplate (holding the DB connection) to successfully
	 * "update" into the DB
	 * 
	 * @throws RollbackException
	 */

	@SuppressWarnings("unchecked")
	@Test
	public void editSystemAdvisoryHappyPathTest() throws RollbackException {
		SystemAdvisory systemAdvisory = TestUtils.getMockAdvisories(1).get(0);
		when(jdbcTemplateMock.update(any(String.class), any(Map.class))).thenReturn(1);

		SystemAdvisory returnedAdvisory = systemAdvisoryDao.editSystemAdvisory(systemAdvisory);
		assertNotNull(returnedAdvisory);
	}

	/**
	 * Tests that the editSystemAdvisory method properly handles exceptions
	 * thrown from the database
	 * 
	 * Mocks the JdbcTemplate (holding the DB connection) to throw an Exception
	 * Test requires that the method throws a RollbackException to pass
	 * 
	 * @throws RollbackException
	 */
	@SuppressWarnings("unchecked")
	@Test(expected = RollbackException.class)
	public void editSystemAdvisoryExceptionsTest() throws RollbackException {
		SystemAdvisory systemAdvisory = TestUtils.getMockAdvisories(1).get(0);
		systemAdvisory.setId("548");
		when(jdbcTemplateMock.update(any(String.class), any(Map.class))).thenThrow(new DataAccessResourceFailureException("An exception was thrown when updating an advisory in the DB."));
		systemAdvisoryDao.editSystemAdvisory(systemAdvisory);
	}
	
	/**
	 * Tests that the normal behavior of the archiveSystemAdvisory method properly
	 * update into the DB
	 * 
	 * Mocks the JdbcTemplate (holding the DB connection) to successfully
	 * archive the advisory in the DB
	 * 
	 * @throws RollbackException
	 */

	@SuppressWarnings("unchecked")
	@Test
	public void archiveSystemAdvisoryHappyPathTest() throws RollbackException {
		SystemAdvisory systemAdvisory = TestUtils.getMockAdvisories(1).get(0);
		when(jdbcTemplateMock.update(any(String.class), any(Map.class))).thenReturn(1);

		SystemAdvisory returnedAdvisory = systemAdvisoryDao.archiveSystemAdvisory(systemAdvisory);
		assertNotNull(returnedAdvisory);
	}

	/**
	 * Tests that the archiveSystemAdvisory method properly handles exceptions
	 * thrown from the database
	 * 
	 * Mocks the JdbcTemplate (holding the DB connection) to throw an Exception
	 * Test requires that the method throws a RollbackException to pass
	 * 
	 * @throws RollbackException
	 */
	@SuppressWarnings("unchecked")
	@Test(expected = RollbackException.class)
	public void archiveSystemAdvisoryExceptionsTest() throws RollbackException {
		SystemAdvisory systemAdvisory = TestUtils.getMockAdvisories(1).get(0);
		when(jdbcTemplateMock.update(any(String.class), any(Map.class))).thenThrow(new DataAccessResourceFailureException("An exception was thrown when updating an advisory in the DB."));
		systemAdvisoryDao.archiveSystemAdvisory(systemAdvisory);
	}

	/**
	 * Tests that the getSystemAdvisoryById method properly handles the expected behavior of
	 * returning the system advisory corresponding to the passed ID
	 * 
	 * Mocks the JdbcTemplate to return a list containing one system advisory object
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void getSystemAdvisoryByIdHappyPathTest() {
		try {
			when(jdbcTemplateMock.query(any(String.class), any(Map.class), any(SystemAdvisoryRowMapper.class))).thenReturn(TestUtils.getMockAdvisories(1));
			SystemAdvisory systemAdvisory = systemAdvisoryDao.getSystemAdvisoryById(1);
			assertNotNull(systemAdvisory);
		} catch (RollbackException e) {
			fail("Did not expect an exception to be thrown.");
		}

	}

	/**
	 * Tests that the getSystemAdvisoryById method properly handles the expected behavior of when the 
	 * DB fails to find an advisory with the given ID
	 * 
	 * Mocks the JdbcTemplate to return an empty list of system advisories
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void getSystemAdvisoryByIdNotFoundTest() {
		try {
			when(jdbcTemplateMock.query(any(String.class), any(Map.class), any(SystemAdvisoryRowMapper.class))).thenReturn(new ArrayList<SystemAdvisory>());
			SystemAdvisory systemAdvisory = systemAdvisoryDao.getSystemAdvisoryById(1);
			assertNull(systemAdvisory);
		} catch (RollbackException e) {
			fail("Did not expect an exception to be thrown.");
		}

	}

	/**
	 * Tests that the getSystemAdvisoryById method properly handles exceptions
	 * thrown from the database
	 * 
	 * Mocks the JdbcTemplate (holding the DB connection) to throw an Exception
	 * Test requires that the method throws a RollbackException to pass
	 * 
	 * @throws RollbackException
	 */
	@SuppressWarnings("unchecked")
	@Test(expected = RollbackException.class)
	public void getSystemAdvisoryByIdExceptionTest() throws Exception {
		when(jdbcTemplateMock.query(any(String.class), any(Map.class), any(SystemAdvisoryRowMapper.class))).thenThrow(new DataAccessResourceFailureException("Something went wrong"));
		systemAdvisoryDao.getSystemAdvisoryById(1);

	}
	

}
