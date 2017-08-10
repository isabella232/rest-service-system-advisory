package gov.nsf.systemadvisoryservice.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import gov.nsf.systemadvisoryservice.common.TestUtils;
import gov.nsf.systemadvisoryservice.common.exception.RollbackException;
import gov.nsf.systemadvisoryservice.model.SystemAdvisory;
import gov.nsf.systemadvisoryservice.model.SystemAdvisoryWrapper;
import gov.nsf.systemadvisoryservice.service.SystemAdvisoryService;

/**
 * Unit Tests for Spring System Advisory Controller
 * 
 * @author jlinden
 * 
 */
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
@WebAppConfiguration
public class SystemAdvisoryControllerTest {
	private final String ACTIVE_ONLY = "ALL";

	@InjectMocks
	private SystemAdvisoryController controller;

	private MockMvc mockMvc;

	@Mock
	private SystemAdvisoryService serviceMock;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	/**
	 * Tests that a GET request returns an 200OK JSON response with several
	 * mocked advisory objects.
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void getRequestHappyPathTest() throws Exception {
		ArrayList<SystemAdvisory> mockedAdvisories = TestUtils.getMockAdvisories(1, 2, 3, 4);

		when(serviceMock.getSystemAdvisories(any(List.class), any(String.class))).thenReturn(mockedAdvisories);
		mockMvc.perform(get("/systemAdvisories")).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$.systemAdvisories", hasSize(mockedAdvisories.size())));

		verify(serviceMock, times(1)).getSystemAdvisories(new ArrayList<String>(), ACTIVE_ONLY);

	}

	/**
	 * Tests that a GET request that results in a server error (exception being
	 * thrown) occurs somewhere during the transaction will result in a 5xx
	 * response code.
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void getRequestServerErrorTest() throws Exception {
		when(serviceMock.getSystemAdvisories(any(List.class), any(String.class))).thenThrow(new RollbackException());
		mockMvc.perform(get("/systemAdvisories")).andExpect(status().is5xxServerError());

		verify(serviceMock, times(1)).getSystemAdvisories(new ArrayList<String>(), ACTIVE_ONLY);

	}
	
	/**
	 * Tests that a POST request that successfully inserts an advisory into the DB
	 * returns a 200 OK response.
	 * 
	 */
	@Test
	public void postRequestHappyPathTest() throws Exception{
		SystemAdvisory sysAdv = new SystemAdvisory();
		sysAdv.setAppName("MyNSF");
		sysAdv.setAppAreaName("System Advisory Service");
		sysAdv.setAdvisoryType("Informational");
		sysAdv.setAdvisoryTitle("Controller test");
		sysAdv.setAdvisoryText("This is a test for the system adivosry post controller");
		sysAdv.setAdvisoryURL("");
		sysAdv.setAdvisoryEffectiveDate("2030-06-06 11:00:00.0");
		sysAdv.setAdvisoryExpiryDate("2030-06-11 15:00:00.0");
		sysAdv.setInputProgram("");
		sysAdv.setInputUser("");
		sysAdv.setInputTimeStamp("");
		sysAdv.setLastUpdateProgram("Dunno");
		sysAdv.setLastUpdateUser("jlinden");
		sysAdv.setLastUpdateTimestamp("");		
		
		when(serviceMock.createSystemAdvisory(any(SystemAdvisory.class))).thenReturn(new SystemAdvisoryWrapper(sysAdv));
		mockMvc.perform(post("/systemAdvisories").content(new ObjectMapper().writeValueAsString(sysAdv)).contentType(MediaType.APPLICATION_JSON)).
			andExpect(status().is2xxSuccessful());
		
	}
	
	/**
	 * Tests that a POST request that results in a rollback exception somewhere returns a 500 response.
	 * 
	 */
	@Test
	public void postRequestRollbackServerErrorTest() throws Exception{
		SystemAdvisory sysAdv = new SystemAdvisory();
		sysAdv.setAppName("MyNSF");
		sysAdv.setAppAreaName("System Advisory Service");
		sysAdv.setAdvisoryType("Informational");
		sysAdv.setAdvisoryTitle("Controller test");
		sysAdv.setAdvisoryText("This is a test for the system adivosry post controller");
		sysAdv.setAdvisoryURL("");
		sysAdv.setAdvisoryEffectiveDate("2030-06-06 11:00:00.0");
		sysAdv.setAdvisoryExpiryDate("2030-06-11 15:00:00.0");
		sysAdv.setInputProgram("");
		sysAdv.setInputUser("");
		sysAdv.setInputTimeStamp("");
		sysAdv.setLastUpdateProgram("Dunno");
		sysAdv.setLastUpdateUser("jlinden");
		sysAdv.setLastUpdateTimestamp("");		
		
		when(serviceMock.createSystemAdvisory(any(SystemAdvisory.class))).thenThrow(new RollbackException("Some Rollback exception occurred"));
		mockMvc.perform(post("/systemAdvisories").content(new ObjectMapper().writeValueAsString(sysAdv)).contentType(MediaType.APPLICATION_JSON)).
			andExpect(status().is5xxServerError());
		
	}
	
	
	/**
	 * Tests that a POST request containing a system advisory object with invalid fields returns a
	 * Http.BAD_REQUEST response.
	 * 
	 */
	@Test
	public void postRequestValidationErrorTest() throws Exception{
		SystemAdvisory sysAdv = new SystemAdvisory();
		sysAdv.setAppName(null);
		sysAdv.setAppAreaName(null);
		sysAdv.setAdvisoryType(null);

		
		mockMvc.perform(post("/systemAdvisories").content(new ObjectMapper().writeValueAsString(sysAdv)).contentType(MediaType.APPLICATION_JSON)).
			andExpect(status().isBadRequest());
		
	}
	
	/**
	 * Tests that a Put request that successfully update an advisory 
	 * 	 * returns a 200 OK response.
	 * 
	 */
	@Test
	public void editSystemAdvisoryHappyPathTest() throws Exception{
		SystemAdvisory systemAdvisory = new SystemAdvisory();
		systemAdvisory.setId("548");
		systemAdvisory.setAppName("MyNSF");
		systemAdvisory.setAppAreaName("System Advisory Service");		
		systemAdvisory.setAdvisoryType("Informational");
		systemAdvisory.setAdvisoryTitle("Controller test");
		systemAdvisory.setAdvisoryText("This is a test for the system adivosry update controller");
		systemAdvisory.setAdvisoryURL("test");
		systemAdvisory.setAdvisoryEffectiveDate("2016-06-11 11:00:00.0");
		systemAdvisory.setAdvisoryExpiryDate("2016-06-19 23:00:00.0");
		systemAdvisory.setLastUpdateProgram("test");
		systemAdvisory.setLastUpdateUser("amahabal");
		systemAdvisory.setLastUpdateTimestamp("2016-06-21 01:22:00.0");		
		
		when(serviceMock.editSystemAdvisory(any(SystemAdvisory.class))).thenReturn(new SystemAdvisoryWrapper(systemAdvisory));
		mockMvc.perform(put("/systemAdvisories/548").content(new ObjectMapper().writeValueAsString(systemAdvisory)).contentType(MediaType.APPLICATION_JSON)).
			andExpect(status().is2xxSuccessful());
		
	}
	/**
	 * Tests that a Put request that results in a rollback exception somewhere returns a 500 response.
	 * 
	 */
	@Test
	public void editRequestRollbackServerErrorTest() throws Exception{
		
		SystemAdvisory systemAdvisory = new SystemAdvisory();
		systemAdvisory.setId("548");
		systemAdvisory.setAppName("MyNSF");
		systemAdvisory.setAppAreaName("System Advisory Service");				
		systemAdvisory.setAdvisoryType("Informational");
		systemAdvisory.setAdvisoryTitle("Controller test");
		systemAdvisory.setAdvisoryText("This is a test for the system adivosry update controller");
		systemAdvisory.setAdvisoryURL("test");
		systemAdvisory.setAdvisoryEffectiveDate("2016-06-06 11:00:00.0");
		systemAdvisory.setAdvisoryExpiryDate("2016-06-11 15:00:00.0");
		systemAdvisory.setLastUpdateProgram("test");
		systemAdvisory.setLastUpdateUser("amahabal");
		systemAdvisory.setLastUpdateTimestamp("2016-06-16 15:00:00.0");		
		
		when(serviceMock.editSystemAdvisory(any(SystemAdvisory.class))).thenThrow(new RollbackException("Some Rollback exception occurred"));
		mockMvc.perform(put("/systemAdvisories/548").content(new ObjectMapper().writeValueAsString(systemAdvisory)).contentType(MediaType.APPLICATION_JSON)).
			andExpect(status().is5xxServerError());
		
	}	
	
	/**
	 * Tests that a Delete request that successfully archives an advisory 
	 * returns a 200 OK response.
	 * 
	 */
	@Test
	public void archiveSystemAdvisoryHappyPathTest() throws Exception{
		SystemAdvisory systemAdvisory = new SystemAdvisory();
		systemAdvisory.setId("548");
		systemAdvisory.setAppName("MyNSF");
		systemAdvisory.setAppAreaName("System Advisory Service");		
		systemAdvisory.setAdvisoryType("Informational");
		systemAdvisory.setAdvisoryTitle("Controller test");
		systemAdvisory.setAdvisoryText("This is a test for the system advisory delete (archive) path");
		systemAdvisory.setAdvisoryURL("test");
		systemAdvisory.setAdvisoryEffectiveDate("2016-06-11 11:00:00.0");
		systemAdvisory.setAdvisoryExpiryDate("2016-06-19 23:00:00.0");
		systemAdvisory.setLastUpdateProgram("test");
		systemAdvisory.setLastUpdateUser("jlinden");
		systemAdvisory.setLastUpdateTimestamp("2016-06-21 01:22:00.0");		
		
		when(serviceMock.archiveSystemAdvisory(any(SystemAdvisory.class))).thenReturn(new SystemAdvisoryWrapper(systemAdvisory));
		mockMvc.perform(delete("/systemAdvisories/548").content(new ObjectMapper().writeValueAsString(systemAdvisory)).contentType(MediaType.APPLICATION_JSON)).
			andExpect(status().is2xxSuccessful());
		
	}
	/**
	 * Tests that a Delete request that results in a rollback exception somewhere returns a 500 response.
	 * 
	 */
	@Test
	public void archiveSystemAdvisoryRollbackServerErrorTest() throws Exception{
		
		SystemAdvisory systemAdvisory = new SystemAdvisory();
		systemAdvisory.setId("548");
		systemAdvisory.setAppName("MyNSF");
		systemAdvisory.setAppAreaName("System Advisory Service");				
		systemAdvisory.setAdvisoryType("Informational");
		systemAdvisory.setAdvisoryTitle("Controller test");
		systemAdvisory.setAdvisoryText("This is a test for the system advisory delete (archive) path");
		systemAdvisory.setAdvisoryURL("test");
		systemAdvisory.setAdvisoryEffectiveDate("2016-06-06 11:00:00.0");
		systemAdvisory.setAdvisoryExpiryDate("2016-06-11 15:00:00.0");
		systemAdvisory.setLastUpdateProgram("test");
		systemAdvisory.setLastUpdateUser("jlinden");
		systemAdvisory.setLastUpdateTimestamp("2016-06-16 15:00:00.0");		
		
		when(serviceMock.archiveSystemAdvisory(any(SystemAdvisory.class))).thenThrow(new RollbackException("Some Rollback exception occurred"));
		mockMvc.perform(delete("/systemAdvisories/548").content(new ObjectMapper().writeValueAsString(systemAdvisory)).contentType(MediaType.APPLICATION_JSON)).
			andExpect(status().is5xxServerError());
		
	}	


}
