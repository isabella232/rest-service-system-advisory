package gov.nsf.systemadvisoryservice.validator;

import gov.mynsf.common.datetime.NsfDateTimeUtil;
import gov.nsf.systemadvisoryservice.common.exception.FormValidationException;
import gov.nsf.systemadvisoryservice.common.util.Constants;
import gov.nsf.systemadvisoryservice.model.BaseError;
import gov.nsf.systemadvisoryservice.model.SystemAdvisory;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * Base class for system advisory validation - Abstract because it should be
 * subclassed for each http method which requires validation
 * 
 * @author jlinden
 * 
 */
public abstract class SystemAdvisoryValidator {

	/**
	 * Checks the fields of the systemAdvisory object for validity. If any
	 * invalid fields are found a FormValidationException will be thrown -
	 * containing the list of errors
	 * 
	 * This method is abstract and should be implemented by sub classes designed 
	 * for each http method request that requires validation of a system advisory object 
	 * 
	 * @param systemAdvisory
	 * @throws FormValidationException
	 */
	public abstract void validateRequest(SystemAdvisory systemAdvisory) throws FormValidationException;

	
	/**
	 * Validates that the data strings for effective and end dates are formatted according to the 
	 * ISO standard
	 * 
	 * @param effectiveDate
	 * @param endDate
	 * @return
	 */
	protected List<BaseError> validateAdvisoryDates(String effectiveDate, String endDate) {

		List<BaseError> errors = new ArrayList<BaseError>();

		if (isInvalidDateFormat(effectiveDate)) {
			errors.add(new BaseError(Constants.ADVY_EFF_DATE, Constants.INVALID_EFF_DATE_FORMAT));
		}

		if (isInvalidDateFormat(endDate)) {
			errors.add(new BaseError(Constants.ADVY_EXPY_DATE, Constants.INVALID_END_DATE_FORMAT));
		}
		return errors;
	}

	/**
	 * Check if the passed string is null or empty (i.e. "")
	 * 
	 * @param field
	 * @return true/false
	 */
	protected boolean isInvalidString(String field) {
		return StringUtils.isEmpty(field);
	}

	/**
	 * Checks if the passed Date string matches the format
	 * "YYYY:MM:DD HH:MM:SS:MS" (See Constants.DATE_TIME_REGEX for the regular
	 * expression pattern)
	 * 
	 * @param date
	 * @return true/false
	 */
	protected boolean isInvalidDateFormat(String dateTimeString) {
		return !NsfDateTimeUtil.isValidDateTime(dateTimeString);
	}
	
	protected boolean isInvalidIdNumberString(String id){
		try {
			Integer.parseInt(id);
		} catch (NumberFormatException e) {
			return true;
		}
		
		return false;
	}
	
	protected boolean errorOccurred(List<BaseError> errors){
		return !errors.isEmpty();
	}
	
	

}
