package gov.nsf.systemadvisoryservice.validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import gov.nsf.systemadvisoryservice.common.exception.FormValidationException;
import gov.nsf.systemadvisoryservice.common.util.Constants;
import gov.nsf.systemadvisoryservice.model.BaseError;
import gov.nsf.systemadvisoryservice.model.SystemAdvisory;

/**
 * Validator class for handling DELETE (soft) requests
 * 
 * @author jlinden
 * 
 */
public class DeleteSystemAdvisoryValidator extends SystemAdvisoryValidator {

	public DeleteSystemAdvisoryValidator() {
		// empty constructor
	}

	/**
	 * Checks the fields of the systemAdvisory object for validity. If any
	 * invalid fields are found a FormValidationException will be thrown -
	 * containing the list of errors
	 * 
	 * @param systemAdvisory
	 * @throws FormValidationException
	 */
	@Override
	public void validateRequest(SystemAdvisory systemAdvisory) throws FormValidationException {
		List<BaseError> errors = new ArrayList<BaseError>();

		errors.addAll(validateAdvisoryStrings(systemAdvisory));
		errors.addAll(validateAdvisoryDates(systemAdvisory.getAdvisoryExpiryDate()));

		if (errorOccurred(errors)) {
			throw new FormValidationException(Constants.INVALID_FORM_DATA, errors);
		}
	}

	/**
	 * Checks if the following fields are non-null and non-empty (i.e. ""):
	 * 
	 * Populates the errors list if any of these invalidities are found
	 * 
	 * @param systemAdvisory
	 * @param errors
	 */
	protected List<BaseError> validateAdvisoryStrings(SystemAdvisory systemAdvisory) {
		List<BaseError> errors = new ArrayList<BaseError>();

		if (isInvalidIdNumberString(systemAdvisory.getId())) {
			errors.add(new BaseError(Constants.ADVY_ID, Constants.MISSING_ADVY_ID));
		}

		if (isInvalidString(systemAdvisory.getAdvisoryExpiryDate())) {
			errors.add(new BaseError(Constants.ADVY_EXPY_DATE, Constants.MISSING_ADVY_EXPY_DATE));
		}
		
		if (isInvalidString(systemAdvisory.getLastUpdateProgram())) {
			errors.add(new BaseError(Constants.LAST_UPDT_PGM, Constants.MISSING_LAST_UPDT_PGM));
		}
		
		if (isInvalidString(systemAdvisory.getLastUpdateUser())) {
			errors.add(new BaseError(Constants.LAST_UPDT_USER, Constants.MISSING_LAST_UPDT_USER));
		}

		return errors;

	}

	/**
	 * Checks if the endDate is a valid date string
	 * 
	 * Populates the errors list if any of these invalidities are found
	 * 
	 * @param systemAdvisory
	 * @param errors
	 */
	protected List<BaseError> validateAdvisoryDates(String endDate) {
		if (isInvalidString(endDate)) {
			return Collections.emptyList();
		}

		List<BaseError> errors = new ArrayList<BaseError>();

		if (isInvalidDateFormat(endDate)) {
			errors.add(new BaseError(Constants.ADVY_EXPY_DATE, Constants.INVALID_END_DATE_FORMAT));
		}

		return errors;

	}

}
