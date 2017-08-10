package gov.nsf.systemadvisoryservice.dao;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import gov.mynsf.common.datetime.NsfDateTimeUtil;
import gov.nsf.systemadvisoryservice.common.exception.RollbackException;
import gov.nsf.systemadvisoryservice.model.SystemAdvisory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



/**
 * Unit tests for SystemAdvisoryDaoImpl
 * 
 * @author jlinden
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Ignore
@ContextConfiguration(locations = { "SystemAdvisoryDaoTest-Context.xml" })
public class SystemAdvisoryDaoIntgTest {

	@Autowired
	private SystemAdvisoryDaoImpl systemAdvisoryDao;

	@Test
	public void getActiveMyNSFandEJacketAdvisoriesFromDBTest() {
		List<String> applicationNames = new ArrayList<String>();
		applicationNames.add("MyNSF");
		applicationNames.add("eJacket");
		try {
			Collection<SystemAdvisory> systemAdvs = systemAdvisoryDao.getSystemAdvisories(applicationNames,"true");
			assertNotNull(systemAdvs);
			for( SystemAdvisory adv : systemAdvs ){
				System.out.println(adv);
			}
		} catch (RollbackException e) {
			fail("Did not expect a RollbackException");
			e.printStackTrace();
		} 
		
		
	}
	
	@Test
	public void getAllAdvisoriesFromDBTest() {
		List<String> applicationNames = new ArrayList<String>();

		try {
			Collection<SystemAdvisory> systemAdvs = systemAdvisoryDao.getSystemAdvisories(applicationNames,"true");
			assertNotNull(systemAdvs);
			for( SystemAdvisory adv : systemAdvs ){
				System.out.println(adv);
			}
		} catch (RollbackException e) {
			fail("Did not expect a RollbackException");
			e.printStackTrace();
		} 
		
		
	}
	
	@Test
	public void getActiveMyNSFAdvisoriesFromDBTest() {
		List<String> applicationNames = new ArrayList<String>();
		applicationNames.add("MyNSF");
		try {
			Collection<SystemAdvisory> systemAdvs = systemAdvisoryDao.getSystemAdvisories(applicationNames,"true");
			assertNotNull(systemAdvs);
			for( SystemAdvisory adv : systemAdvs ){
				System.out.println(adv);
			}
		} catch (RollbackException e) {
			fail("Did not expect a RollbackException");
			e.printStackTrace();
		} 
		
		
	}
	
	@Test
	public void getActiveEJacketAdvisoriesFromDBTest() {
		List<String> applicationNames = new ArrayList<String>();
		applicationNames.add("eJacket");
		try {
			Collection<SystemAdvisory> systemAdvs = systemAdvisoryDao.getSystemAdvisories(applicationNames,"true");
			assertNotNull(systemAdvs);
			for( SystemAdvisory adv : systemAdvs ){
				System.out.println(adv);
			}
		} catch (RollbackException e) {
			fail("Did not expect a RollbackException");
			e.printStackTrace();
		} 
		
		
	}
	
	@Test
	public void getInactiveMyNSFandEJacketAdvisoriesFromDBTest() {
		List<String> applicationNames = new ArrayList<String>();
		applicationNames.add("MyNSF");
		applicationNames.add("eJacket");
		try {
			Collection<SystemAdvisory> systemAdvs = systemAdvisoryDao.getSystemAdvisories(applicationNames,"false");
			assertNotNull(systemAdvs);
			for( SystemAdvisory adv : systemAdvs ){
				System.out.println(adv);
			}
		} catch (RollbackException e) {
			fail("Did not expect a RollbackException");
			e.printStackTrace();
		} 
		
		
	}
	
	@Test
	public void getInactiveMyNSFAdvisoriesFromDBTest() {
		List<String> applicationNames = new ArrayList<String>();
		applicationNames.add("MyNSF");
		try {
			Collection<SystemAdvisory> systemAdvs = systemAdvisoryDao.getSystemAdvisories(applicationNames,"false");
			assertNotNull(systemAdvs);
			for( SystemAdvisory adv : systemAdvs ){
				System.out.println(adv);
			}
		} catch (RollbackException e) {
			fail("Did not expect a RollbackException");
			e.printStackTrace();
		} 
		
		
	}
	
	@Test
	public void getInactiveEJacketAdvisoriesFromDBTest() {
		List<String> applicationNames = new ArrayList<String>();
		applicationNames.add("eJacket");
		try {
			Collection<SystemAdvisory> systemAdvs = systemAdvisoryDao.getSystemAdvisories(applicationNames,"false");
			assertNotNull(systemAdvs);
			for( SystemAdvisory adv : systemAdvs ){
				System.out.println(adv);
			}
		} catch (RollbackException e) {
			fail("Did not expect a RollbackException");
			e.printStackTrace();
		} 
		
		
	}
	@Test
	public void postSystemAdvisory(){
		SystemAdvisory systemAdvisory = new SystemAdvisory();
		systemAdvisory.setAdvisoryTitle("TEST INSERT");
		systemAdvisory.setAdvisoryText("This is a test for POST");
		systemAdvisory.setAdvisoryType("Alert");
		systemAdvisory.setAppName("MyNSF");
		systemAdvisory.setAdvisoryEffectiveDate("2016-06-06 11:00:00.0");
		systemAdvisory.setAdvisoryExpiryDate("2016-06-11 15:00:00.0");
		systemAdvisory.setLastUpdateUser("Someone");
		systemAdvisory.setLastUpdateProgram("MyNSF");
		
		try {
			systemAdvisory = systemAdvisoryDao.createSystemAdvisory(systemAdvisory);
			System.out.println(systemAdvisory);
		} catch (RollbackException e) {
			fail();
			e.printStackTrace();
		}

	}

				
	@Test
	public void testEditSystemAdvisory(){
		SystemAdvisory systemAdvisory = new SystemAdvisory();
		systemAdvisory.setAdvisoryType("Alert");
		systemAdvisory.setAdvisoryTitle("TEST UPDATE");
		systemAdvisory.setAdvisoryText("This is a test for Update");
		systemAdvisory.setAdvisoryEffectiveDate("2016-06-18 11:00:00.0");
		systemAdvisory.setAdvisoryExpiryDate("2016-06-26 10:00:00.0");
		systemAdvisory.setAdvisoryURL("test");		
		systemAdvisory.setLastUpdateProgram("MyNSF");
		systemAdvisory.setLastUpdateUser("amahabal");	
		systemAdvisory.setLastUpdateTimestamp("2016-06-21 02:03:00.0");
		systemAdvisory.setId("593");
		try {
			systemAdvisory = systemAdvisoryDao.editSystemAdvisory(systemAdvisory);
			System.out.println(systemAdvisory);
		} catch (RollbackException e) {
			fail();
			e.printStackTrace();
		}
	
	}
	
	@Test
	public void testArchiveSystemAdvisory(){
		SystemAdvisory systemAdvisory = new SystemAdvisory();
		systemAdvisory.setAdvisoryExpiryDate(NsfDateTimeUtil.convertToSybaseDateTimeString(new DateTime().toString()));
		systemAdvisory.setId("593");
		systemAdvisory.setLastUpdateProgram("MyNSF");
		systemAdvisory.setLastUpdateUser("sasvc");

		try {
			systemAdvisory = systemAdvisoryDao.archiveSystemAdvisory(systemAdvisory);
			Collection<SystemAdvisory> advs = systemAdvisoryDao.getSystemAdvisories(Collections.EMPTY_LIST, "false");
			for( SystemAdvisory a : advs ){
				System.out.println(a);
			}
		} catch (RollbackException e) {
			e.printStackTrace();
			fail();
		}
	
	}

}
