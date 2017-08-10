package gov.nsf.systemadvisoryservice.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nsf.systemadvisoryservice.common.TestUtils;
import gov.nsf.systemadvisoryservice.common.exception.FormValidationException;
import gov.nsf.systemadvisoryservice.common.util.Constants;
import gov.nsf.systemadvisoryservice.model.BaseError;
import gov.nsf.systemadvisoryservice.model.SystemAdvisory;

import java.util.List;

import org.junit.Test;

public class PostSystemAdvisoryValidatorTest {

	private PostSystemAdvisoryValidator validator = new PostSystemAdvisoryValidator();
	
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
		systemAdvisory.setAppName(null);
		systemAdvisory.setAppAreaName(null);
		systemAdvisory.setAdvisoryTitle(null);
		systemAdvisory.setAdvisoryType(null);
		systemAdvisory.setAdvisoryEffectiveDate(null);
		systemAdvisory.setAdvisoryExpiryDate(null);
		systemAdvisory.setLastUpdateUser(null);
		systemAdvisory.setLastUpdateProgram(null);

		List<BaseError> errors = validator.validateAdvisoryStrings(systemAdvisory);
		
		assertEquals(errors.size(), 8);
	}

	/**
	 * Tests that the validateAdvisoryStrings method returns a List of errors.
	 * For every required non-empty-string system advisory field, an error will be added if said value
	 * is an empty string.
	 */
	@Test
	public void validateAdvisoryStringsEmptyStringFeildsTest() {
		SystemAdvisory systemAdvisory = TestUtils.getMockAdvisories(1).get(0);
		systemAdvisory.setAppName(Constants.EMPTY_STRING);
		systemAdvisory.setAppAreaName(Constants.EMPTY_STRING);
		systemAdvisory.setAdvisoryTitle(Constants.EMPTY_STRING);
		systemAdvisory.setAdvisoryType(Constants.EMPTY_STRING);
		systemAdvisory.setAdvisoryEffectiveDate(Constants.EMPTY_STRING);
		systemAdvisory.setAdvisoryExpiryDate(Constants.EMPTY_STRING);
		systemAdvisory.setLastUpdateUser(Constants.EMPTY_STRING);
		systemAdvisory.setLastUpdateProgram(Constants.EMPTY_STRING);

		List<BaseError> errors = validator.validateAdvisoryStrings(systemAdvisory);
		assertEquals(errors.size(), 8);
	}
	
	/**
	 * Tests that the validateAdvisoryDates method handles the happy path where no
	 * errors are encountered. An empty list should be returned
	 */
	@Test
	public void validateAdvisoryDatesHappyPathTest() {
		String effectiveDate = "2030-04-29T10:15:00.500+00:00";
		String endDate = "2030-04-29T10:15:00.500+00:00";

		List<BaseError> errors = validator.validateAdvisoryDates(effectiveDate, endDate);
		assertEquals(0,errors.size());
	}

	/**
	 * Tests that the validateAdvisoryDates method will short circuit and return no errors 
	 * if either dateString is null or empty. An empty list should be returned
	 */
	@Test
	public void validateAdvisoryDatesInvalidDateStringsTest() {
		String effectiveDate = "";
		String endDate = "";

		List<BaseError> errors = validator.validateAdvisoryDates(effectiveDate, endDate);
		assertEquals(errors.size(), 0);
	}

	/**
	 * Tests that the validateAdvisoryDates method returns a list of errors ( one for 
	 * each ill-formatted dateString ). A list with two errors should be returned 
	 */
	@Test
	public void validateAdvisoryDatesIllFormattedDateStringsTest() {
		String effectiveDate = "a1-06-06 11:00:00.0";
		String endDate = "2016-126-06 15:00:00.0";

		List<BaseError> errors = validator.validateAdvisoryDates(effectiveDate, endDate);

		assertEquals(2,errors.size());
	}

	/**
	 * Tests that the validateAdvisoryDates method returns an error if the end date is 
	 * before the effective date.
	 */
	@Test
	public void validateAdvisoryDatesEndBeforeEffectiveTest() {
		String effectiveDate = "2030-06-06 15:00:00.0";
		String endDate = "2030-06-06 11:00:00.0";

		List<BaseError> errors = validator.validateAdvisoryDates(effectiveDate, endDate);
		assertEquals(errors.size(), 1);
		BaseError expectedError = new BaseError(Constants.ADVY_EXPY_DATE, Constants.END_DATE_BEFORE_EFF_DATE);
		BaseError observedError = errors.get(0);
		assertTrue(observedError.equals(expectedError));
	}
	
	/**
	 * Tests that the validateAdvisoryDates method returns an error of the effective date comes before
	 * the current date.
	 */
	@Test
	public void validateAdvisoryDatesEffBeforeCurrentTest() {
		String effectiveDate = "2016-06-21 15:00:00.0";
		String endDate = "2016-06-29 15:00:00.0";

		List<BaseError> errors = validator.validateAdvisoryDates(effectiveDate, endDate);
		assertEquals(errors.size(), 1);
		BaseError expectedError = new BaseError(Constants.ADVY_EFF_DATE, Constants.EFF_DATE_BEFORE_CURR_DATE);
		BaseError observedError = errors.get(0);
		assertTrue(observedError.equals(expectedError));
	}
}
