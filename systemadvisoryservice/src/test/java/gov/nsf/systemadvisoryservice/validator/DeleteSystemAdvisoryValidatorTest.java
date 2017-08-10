package gov.nsf.systemadvisoryservice.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import gov.nsf.systemadvisoryservice.common.TestUtils;
import gov.nsf.systemadvisoryservice.common.exception.FormValidationException;
import gov.nsf.systemadvisoryservice.common.util.Constants;
import gov.nsf.systemadvisoryservice.model.BaseError;
import gov.nsf.systemadvisoryservice.model.SystemAdvisory;

import java.util.List;

import org.junit.Test;

public class DeleteSystemAdvisoryValidatorTest {

	DeleteSystemAdvisoryValidator validator = new DeleteSystemAdvisoryValidator();
	
	/**
	 * Tests that the validateRequest method properly handles the happy path when 
	 * no errors are encountered. The method should exit with no exception being thrown. 
	 */
	@Test
	public void validateRequestHappyPathTest() {
		SystemAdvisory systemAdvisory = TestUtils.getMockAdvisories(1).get(0);

		systemAdvisory.setAdvisoryEffectiveDate("2030-06-06 11:00:00.0");
		systemAdvisory.setAdvisoryExpiryDate("2030-06-06 15:00:00.0");
		try {
			validator.validateRequest(systemAdvisory);
		} catch (FormValidationException e) {
			fail("Did not expect an exception to be thrown.");
		}

	}
	
	/**
	 * Tests that the validateRequest method throws a FormValidationException of any errors
	 * are encountered
	 * 
	 * @throws FormValidationException
	 */
	@Test(expected=FormValidationException.class)
	public void validateRequestErrorsFoundTest() throws FormValidationException {
		SystemAdvisory systemAdvisory = TestUtils.getMockAdvisories(1).get(0);
		systemAdvisory.setId("fish");
		validator.validateRequest(systemAdvisory);		
	}
	
	/**
	 * Tests that the validateAdvisoryStrings method handles the happy path where
	 * no errors are encountered. An empty list should be returned
	 */
	@Test
	public void validateAdvisoryStringsHappyPathTest() {
		SystemAdvisory systemAdvisory = TestUtils.getMockAdvisories(1).get(0);

		List<BaseError> errors = validator.validateAdvisoryStrings(systemAdvisory);
		assertEquals(errors.size(), 0);
	}

	/**
	 * Tests that the validateAdvisoryStrings method returns a List of errors.
	 * For every required non-null system advisory field, an error will be added if said value
	 * is null.
	 */
	@Test
	public void validateAdvisoryStringsNullStringFeildsTest() {
		SystemAdvisory systemAdvisory = TestUtils.getMockAdvisories(1).get(0);
		
		systemAdvisory.setId(null);
		systemAdvisory.setAdvisoryExpiryDate(null);

		List<BaseError> errors = validator.validateAdvisoryStrings(systemAdvisory);
		
		assertEquals(errors.size(), 2);
	}

	/**
	 * Tests that the validateAdvisoryStrings method returns a List of errors.
	 * For every required non-empty string system advisory field, an error will be added if said value
	 * is the empty-string.
	 */
	@Test
	public void validateAdvisoryStringsEmptyStringFeildsTest() {
		SystemAdvisory systemAdvisory = TestUtils.getMockAdvisories(1).get(0);
		
		systemAdvisory.setId(Constants.EMPTY_STRING);
		systemAdvisory.setAdvisoryExpiryDate(Constants.EMPTY_STRING);

		List<BaseError> errors = validator.validateAdvisoryStrings(systemAdvisory);
		
		assertEquals(errors.size(), 2);
	}

	/**
	 * Tests that the validateAdvisoryDates method handles the happy path where no
	 * errors are encountered. An empty list should be returned
	 */
	@Test
	public void validateAdvisoryDatesHappyPathTest() {
		String endDate = "2030-04-29T10:15:00.500+00:00";

		List<BaseError> errors = validator.validateAdvisoryDates(endDate);
		assertEquals(0,errors.size());
	}

	/**
	 * Tests that the validateAdvisoryDates method handles the case when empty strings are passed.
	 * The method should short-circuit and return an empty list
	 */
	@Test
	public void validateAdvisoryDatesInvalidDateStringsTest() {
		String endDate = "";

		List<BaseError> errors = validator.validateAdvisoryDates(endDate);
		assertEquals(errors.size(), 0);
	}

	/**
	 * Tests that the validateAdvisoryDates method handles the case when invalid end date string is passed.
	 * The method should return a list with an error 
	 */
	@Test
	public void validateAdvisoryDatesIllFormattedDateStringsTest() {
		String endDate = "2016-126-06 15:00:00.0";

		List<BaseError> errors = validator.validateAdvisoryDates(endDate);

		assertEquals(1,errors.size());
	}

}
