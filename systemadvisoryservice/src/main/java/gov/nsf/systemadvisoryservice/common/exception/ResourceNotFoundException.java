package gov.nsf.systemadvisoryservice.common.exception;



import org.apache.log4j.Logger;

/**
 * 
 * @author vnayak
 * Description : Class to handle and throw Resource Not Found errors
 */

public class ResourceNotFoundException extends Exception {
	private static final long serialVersionUID = 599L;
	private static final Logger LOGGER = Logger.getLogger(ResourceNotFoundException.class);

	/*
	 * No argument constructor
	 */
	public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(String msg) {
        super(msg);
		LOGGER.error(msg);
    }

	public ResourceNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
		LOGGER.error(msg,cause);
	}
	

}
