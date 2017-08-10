package gov.nsf.systemadvisoryservice.controller;

import gov.nsf.systemadvisoryservice.common.exception.FormValidationException;
import gov.nsf.systemadvisoryservice.common.exception.RollbackException;
import gov.nsf.systemadvisoryservice.common.util.Constants;
import gov.nsf.systemadvisoryservice.model.BaseError;
import gov.nsf.systemadvisoryservice.model.SystemAdvisoryWrapper;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vnayak Description : Base Controller Class to server Exception/Error
 *         handling mechanism to all sub class Controllers
 */

@RestController
public class SystemAdvisoryBaseController {
	
	/**
	 * @param FormValidationException
	 * @return ValidationErrorDTO Description - Method to handle runtime
	 *         exceptions/errors caused while serving requests for any of the
	 *         routes in all available Controllers
	 */
	@ExceptionHandler({ FormValidationException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public SystemAdvisoryWrapper processFormValidationException(FormValidationException ex) {
		SystemAdvisoryWrapper response = new SystemAdvisoryWrapper();
		response.setErrors(ex.getValidationErrors());
		return response;
	}

	/**
	 * @param RollbackException
	 * @return ValidationErrorDTO Description - Method to handle runtime
	 *         exceptions/errors caused while serving requests for any of the
	 *         routes in all available Controllers
	 */
	@ExceptionHandler({ RollbackException.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public SystemAdvisoryWrapper processResourceNotFoundException(RollbackException ex) {
		SystemAdvisoryWrapper response = new SystemAdvisoryWrapper();
		response.addError(new BaseError(Constants.DB_TRANSACTION_ERROR, ex.getErrMsg()));
		return response;
	}
	
	/**
	 * @param AccessDeniedException
	 * @return ValidationErrorDTO Description - Method to handle runtime
	 *         exceptions/errors caused while serving requests for any of the
	 *         routes in all available Controllers
	 */
	@ExceptionHandler({ AccessDeniedException.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public SystemAdvisoryWrapper processException(AccessDeniedException ex) {
		SystemAdvisoryWrapper response = new SystemAdvisoryWrapper();
		response.addError(new BaseError(Constants.ACCESS_DENIED_EXCEPTION, ex.getMessage()));
		return response;
	}
	
	/**
	 * @param Exception
	 * @return ValidationErrorDTO Description - Method to handle runtime
	 *         exceptions/errors caused while serving requests for any of the
	 *         routes in all available Controllers
	 */
	@ExceptionHandler({ Exception.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public SystemAdvisoryWrapper processException(Exception ex) {
		SystemAdvisoryWrapper response = new SystemAdvisoryWrapper();
		response.addError(new BaseError(Constants.SERVER_500_ERROR, ex.getMessage()));
		return response;
	}

}
