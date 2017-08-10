package gov.nsf.systemadvisoryservice.validator;

import java.util.ArrayList;

/**
 * Validator class for handling PUT requests
 */
import java.util.List;

import org.joda.time.DateTime;

import gov.mynsf.common.datetime.NsfDateTimeUtil;
import gov.nsf.systemadvisoryservice.common.exception.FormValidationException;
import gov.nsf.systemadvisoryservice.common.util.Constants;
import gov.nsf.systemadvisoryservice.model.SystemAdvisory;
import gov.nsf.systemadvisoryservice.model.BaseError;
import java.util.Collections;

public class PutSystemAdvisoryValidator extends SystemAdvisoryValidator {

	public PutSystemAdvisoryValidator() {
		//Empty constructor
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
		errors.addAll(validateAdvisoryDates(systemAdvisory.getAdvisoryEffectiveDate(), systemAdvisory.getAdvisoryExpiryDate()));

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

		if (isInvalidString(systemAdvisory.getAdvisoryEffectiveDate())) {
			errors.add(new BaseError(Constants.ADVY_EFF_DATE, Constants.MISSING_ADVY_EFF_DATE));
		}

		if (isInvalidString(systemAdvisory.getAdvisoryExpiryDate())) {
			errors.add(new BaseError(Constants.ADVY_EXPY_DATE, Constants.MISSING_ADVY_EXPY_DATE));
		}

		return errors;

	}

	/**
	 * Checks the validity of the Effective Date and End Date inputs
	 * 
	 * Both Date strings must be non-null and must match the expected Date/Time
	 * format. The End Date must not be less than (i.e. come before) the
	 * Effective Date
	 * 
	 * Populates the error list if any of these invalidities are found
	 * 
	 * @param effectiveDate
	 * @param endDate
	 * @param errors
	 */
	@Override
	protected List<BaseError> validateAdvisoryDates(String effectiveDate, String endDate) {
		if (isInvalidString(effectiveDate) || isInvalidString(endDate)) {
			return Collections.emptyList();
		}

		List<BaseError> errors = new ArrayList<BaseError>();
		errors.addAll(super.validateAdvisoryDates(effectiveDate, endDate));

		if (errorOccurred(errors)) { // Short circuit
			return errors;
		}

		DateTime sybaseEffDateObj = NsfDateTimeUtil.getDefaultDateTimeObject(effectiveDate);
		DateTime sybaseEndDateObj = NsfDateTimeUtil.getDefaultDateTimeObject(endDate);

		if (sybaseEndDateObj.toLocalDateTime().isBefore(sybaseEffDateObj.toLocalDateTime())) {
			// a) If the End Date is before the Effective Date, the system shall
			// display the error message,
			// "The End Date must be after the Effective Date." as an inline
			// error.
			errors.add(new BaseError(Constants.ADVY_EXPY_DATE, Constants.END_DATE_BEFORE_EFF_DATE));
		}

		return errors;
	}
}
