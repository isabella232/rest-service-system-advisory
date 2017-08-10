package gov.nsf.systemadvisoryservice.validator;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Factory class for returning system advisory validator classes
 * 
 * @author jlinden
 *
 */
public class SystemAdvisoryValidatorFactoryTest {

	
	/**
	 * Tests that the factory class returns a validator for POST system advisory calls
	 */
	@Test
	public void getPostValidatorTest() {
		SystemAdvisoryValidator validator = SystemAdvisoryValidatorFactory.getValidator(RequestMethod.POST);
		assertTrue(validator instanceof PostSystemAdvisoryValidator);
	}

	/**
	 * Tests that the factory class returns a validator for PUT system advisory calls
	 */
	@Test
	public void getPutValidatorTest() {
		SystemAdvisoryValidator validator = SystemAdvisoryValidatorFactory.getValidator(RequestMethod.PUT);
		assertTrue(validator instanceof PutSystemAdvisoryValidator);
	}

	/**
	 * Tests that the factory class returns a null (should change this eventually - its a bad pattern) if 
	 * an unsupported http method is passed
	 */
	@Test
	public void getValidatorMethodNotSupportedTest() {
		SystemAdvisoryValidator validator = SystemAdvisoryValidatorFactory.getValidator(RequestMethod.TRACE);
		assertTrue(validator == null);
	}
}
