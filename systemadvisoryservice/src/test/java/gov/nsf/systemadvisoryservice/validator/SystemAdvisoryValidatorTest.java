package gov.nsf.systemadvisoryservice.validator;

import static org.junit.Assert.assertEquals;
import gov.nsf.systemadvisoryservice.model.BaseError;

import java.util.List;

import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * JUnit tests for SystemAdvisoryValidator class
 * 
 * @author jlinden
 *
 */
public class SystemAdvisoryValidatorTest {

	SystemAdvisoryValidator validator = SystemAdvisoryValidatorFactory.getValidator(RequestMethod.POST);
	
	/**
	 * Tests that the validateAdvisoryDates method properly handles a valid effective/end dates
	 * being passed by returning an empty list
	 */
	@Test
	public void validateAdvisoryDatesHappyPathTest(){
		String effectiveDate = "2030-04-29T10:15:00.500+00:00";
		String endDate = "2030-04-29T10:15:00.500+00:00";

		List<BaseError> errors = validator.validateAdvisoryDates(effectiveDate, endDate);
		assertEquals(0,errors.size());
	}
	
	/**
	 * Tests that the validateAdvisoryDates method properly handles the case when ill-formatted
	 * date strings are passed
	 * 
	 */
	@Test
	public void validateAdvisoryDatesInvalidFormatStringsTest(){
		String effectiveDate = "fish";
		String endDate = "potato";
		
		List<BaseError> errors = validator.validateAdvisoryDates(effectiveDate, endDate);
		assertEquals(2, errors.size());
	}
}
