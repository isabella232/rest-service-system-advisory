package gov.nsf.systemadvisoryservice.validator;

import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Factory class for returning system advisory validators
 * 
 * @author jlinden
 * 
 */
public class SystemAdvisoryValidatorFactory {

	private SystemAdvisoryValidatorFactory() {
		super();
	}

	/**
	 * Returns the validator associated with the given http method
	 * 
	 * @param httpMethod
	 * @return
	 */
	public static SystemAdvisoryValidator getValidator(RequestMethod httpMethod) {
		SystemAdvisoryValidator validator;

		if (httpMethod == RequestMethod.POST) {
			validator = new PostSystemAdvisoryValidator();
		} else if (httpMethod == RequestMethod.PUT) {
			validator = new PutSystemAdvisoryValidator();
		} else if (httpMethod == RequestMethod.DELETE) {
			validator = new DeleteSystemAdvisoryValidator();
		} else {
			validator = null;
		}

		return validator;

	}
}
