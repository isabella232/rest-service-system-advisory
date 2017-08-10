package gov.nsf.systemadvisoryservice.validator;

import gov.mynsf.common.datetime.NsfDateTimeUtil;
import gov.nsf.systemadvisoryservice.common.exception.FormValidationException;
import gov.nsf.systemadvisoryservice.common.util.Constants;
import gov.nsf.systemadvisoryservice.model.BaseError;
import gov.nsf.systemadvisoryservice.model.SystemAdvisory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;

/**
 * Validator class for handling POST requests
 * 
 * @author jlinden
 *
 */
public class PostSystemAdvisoryValidator extends SystemAdvisoryValidator {

	public PostSystemAdvisoryValidator() {
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

		if (isInvalidString(systemAdvisory.getAppName())) {
			errors.add(new BaseError(Constants.APP_NAME, Constants.MISSING_APP_NAME));
		}

		if (isInvalidString(systemAdvisory.getAppAreaName())) {
			errors.add(new BaseError(Constants.APP_AREA_NAME, Constants.MISSING_APP_AREA_NAME));
		}

		if (isInvalidString(systemAdvisory.getAdvisoryTitle())) {
			errors.add(new BaseError(Constants.ADVY_TITLE, Constants.MISSING_ADVY_TITLE));
		}

		if (isInvalidString(systemAdvisory.getAdvisoryType())) {
			errors.add(new BaseError(Constants.ADVY_TYPE, Constants.MISSING_ADVY_TYPE));
		}

		if (isInvalidString(systemAdvisory.getAdvisoryEffectiveDate())) {
			errors.add(new BaseError(Constants.ADVY_EFF_DATE, Constants.MISSING_ADVY_EFF_DATE));
		}

		if (isInvalidString(systemAdvisory.getAdvisoryExpiryDate())) {
			errors.add(new BaseError(Constants.ADVY_EXPY_DATE, Constants.MISSING_ADVY_EXPY_DATE));
		}

		if (isInvalidString(systemAdvisory.getLastUpdateUser())) {
			errors.add(new BaseError(Constants.LAST_UPDT_USER, Constants.MISSING_LAST_UPDT_USER));
		}

		if (isInvalidString(systemAdvisory.getLastUpdateProgram())) {
			errors.add(new BaseError(Constants.LAST_UPDT_PGM, Constants.MISSING_LAST_UPDT_PGM));
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

		if (!errors.isEmpty()) { // Short circuit
			return errors;
		}

		DateTime sybaseEffDateObj = NsfDateTimeUtil.getDefaultDateTimeObject(effectiveDate);
		DateTime sybaseEndDateObj = NsfDateTimeUtil.getDefaultDateTimeObject(endDate);
		DateTime sybaseCurrDateObj = NsfDateTimeUtil.getDefaultDateTimeObject(new DateTime().minusMinutes(1).toString());

		if (sybaseEndDateObj.toLocalDateTime().isBefore(sybaseEffDateObj.toLocalDateTime())) {
			errors.add(new BaseError(Constants.ADVY_EXPY_DATE, Constants.END_DATE_BEFORE_EFF_DATE));
		}

		if (sybaseEffDateObj.toLocalDateTime().isBefore(sybaseCurrDateObj.toLocalDateTime())) {
			errors.add(new BaseError(Constants.ADVY_EFF_DATE, Constants.EFF_DATE_BEFORE_CURR_DATE));
		}

		return errors;
	}
}
