package gov.nsf.systemadvisoryservice.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import gov.nsf.systemadvisoryservice.common.TestUtils;
import gov.nsf.systemadvisoryservice.common.exception.RollbackException;
import gov.nsf.systemadvisoryservice.dao.SystemAdvisoryDao;
import gov.nsf.systemadvisoryservice.model.SystemAdvisory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit tests for the SystemAdvisoryService
 * 
 * @author jlinden
 * 
 */
@RunWith(MockitoJUnitRunner.class)
public class SystemAdvisoryServiceTest {
	@InjectMocks
	private SystemAdvisoryServiceImpl systemAdvisoryService;

	@Mock
	private SystemAdvisoryDao mockedDAO;

	/**
	 * Tests that the normal behavior of the getSystemAdvisories works as
	 * expected when a list of application names is passed the DAO should be
	 * called for each application name. Mocks the DAO to return a list
	 * SystemAdvisory objects
	 * 
	 * Asserts that the returned list is non-null Asserts that the size of the
	 * returned list equals the expected size
	 * @throws RollbackException 
	 */
	@Test
	public void getSystemAdvisoriesHappyPathTest() throws RollbackException {
		Collection<SystemAdvisory> systemAdvisories = null;
		List<String> applicationNames = new ArrayList<String>();
		applicationNames.add("MyNSF");
		applicationNames.add("eJacket");
		ArrayList<SystemAdvisory> mockAdvisories = TestUtils.getMockAdvisories(1, 2, 3, 4);

		when(mockedDAO.getSystemAdvisories(applicationNames, "true")).thenReturn(mockAdvisories);

		systemAdvisories = systemAdvisoryService.getSystemAdvisories(applicationNames, "true");
		assertNotNull(systemAdvisories);
		assertEquals(systemAdvisories.size(), mockAdvisories.size());
		verify(mockedDAO, times(1)).getSystemAdvisories(applicationNames, "true");
	
	}


	/**
	 * Tests that the getSystemAdvisories method properly handles exceptions
	 * thrown from the data access layer
	 * 
	 * Mocks the dao.getSystemAdvisories to throw a RollbackException Asserts
	 * that this exception was thrown.
	 * @throws RollbackException 
	 * 
	 */
	@Test(expected=RollbackException.class)
	public void getSystemAdvisoriesExceptionsTest() throws RollbackException {
		List<String> applicationNames = new ArrayList<String>();
		applicationNames.add("MyNSF");
		when(mockedDAO.getSystemAdvisories(applicationNames, "true")).thenThrow(new RollbackException("Some exception occured."));
		systemAdvisoryService.getSystemAdvisories(applicationNames, "true");
	}
	
	
	/**
	 * Tests that the createSystemAdvisory method works as expected when no DAO exceptions are thrown.
	 * 
	 * Mocks the dao.createSystemAdvisory to return a System Advisory Object
	 * @throws RollbackException 
	 * 
	 */
	@Test
	public void createSystemAdvisoryHappyPathTest() throws RollbackException {
		SystemAdvisory systemAdvisory = TestUtils.getMockAdvisories(1).get(0);
		SystemAdvisory mockAdvisory = TestUtils.getMockAdvisories(1).get(0);
		when(mockedDAO.createSystemAdvisory(systemAdvisory)).thenReturn(mockAdvisory);
		SystemAdvisory returnedAdvisory = systemAdvisoryService.createSystemAdvisory(systemAdvisory).getSystemAdvisories().get(0);
		assertNotNull(returnedAdvisory);
	}
	
	/**
	 * Tests that the createSystemAdvisory method properly handles exceptions
	 * thrown from the data access layer
	 * 
	 * Mocks the dao.createSystemAdvisory to throw a RollbackException Asserts
	 * that this exception was thrown.
	 * @throws RollbackException 
	 * 
	 */
	@Test(expected=RollbackException.class)
	public void createSystemAdvisoriesExceptionsTest() throws RollbackException {
		SystemAdvisory systemAdvisory = TestUtils.getMockAdvisories(1).get(0);
		
		when(mockedDAO.createSystemAdvisory(systemAdvisory)).thenThrow(new RollbackException("Some exception occured."));
		systemAdvisoryService.createSystemAdvisory(systemAdvisory);
	}

	/**
	 * Tests that the editSystemAdvisory method works as expected when no DAO exceptions are thrown.
	 * 
	 * Mocks the dao.editSystemAdvisory to return a System Advisory Object
	 * @throws RollbackException 
	 * 
	 */
	@Test
	public void editSystemAdvisoryHappyPathTest() throws RollbackException {
		SystemAdvisory systemAdvisory = TestUtils.getMockAdvisories(1).get(0);
		SystemAdvisory mockAdvisory = TestUtils.getMockAdvisories(1).get(0);
		when(mockedDAO.editSystemAdvisory(systemAdvisory)).thenReturn(mockAdvisory);
		SystemAdvisory returnedAdvisory = systemAdvisoryService.editSystemAdvisory(systemAdvisory).getSystemAdvisories().get(0);
		assertNotNull(returnedAdvisory);
	}
	
	/**
	 * Tests that the editSystemAdvisory method properly handles exceptions
	 * thrown from the data access layer
	 * 
	 * Mocks the dao.editSystemAdvisory to throw a RollbackException Asserts
	 * that this exception was thrown.
	 * @throws RollbackException 
	 * 
	 */
	@Test(expected=RollbackException.class)
	public void editSystemAdvisoriesExceptionsTest() throws RollbackException {
		SystemAdvisory systemAdvisory = TestUtils.getMockAdvisories(1).get(0);
		
		when(mockedDAO.editSystemAdvisory(systemAdvisory)).thenThrow(new RollbackException("Some exception occured."));
		systemAdvisoryService.editSystemAdvisory(systemAdvisory);
	}
	
	/**
	 * Tests that the archiveSystemAdvisory method works as expected when no DAO exceptions are thrown.
	 * 
	 * Mocks the dao.archiveSystemAdvisory to return a System Advisory Object
	 * @throws RollbackException 
	 * 
	 */
	@Test
	public void archiveSystemAdvisoryHappyPathTest() throws RollbackException {
		SystemAdvisory systemAdvisory = TestUtils.getMockAdvisories(1).get(0);
		SystemAdvisory mockAdvisory = TestUtils.getMockAdvisories(1).get(0);
		when(mockedDAO.archiveSystemAdvisory(systemAdvisory)).thenReturn(mockAdvisory);
		SystemAdvisory returnedAdvisory = systemAdvisoryService.archiveSystemAdvisory(systemAdvisory).getSystemAdvisories().get(0);
		assertNotNull(returnedAdvisory);
	}
	
	/**
	 * Tests that the archiveSystemAdvisory method properly handles exceptions
	 * thrown from the data access layer
	 * 
	 * Mocks the dao.archiveSystemAdvisory to throw a RollbackException Asserts
	 * that this exception was thrown.
	 * @throws RollbackException 
	 * 
	 */
	@Test(expected=RollbackException.class)
	public void archiveSystemAdvisoriesExceptionsTest() throws RollbackException {
		SystemAdvisory systemAdvisory = TestUtils.getMockAdvisories(1).get(0);
		
		when(mockedDAO.archiveSystemAdvisory(systemAdvisory)).thenThrow(new RollbackException("Some exception occured."));
		systemAdvisoryService.archiveSystemAdvisory(systemAdvisory);
	}	
}
