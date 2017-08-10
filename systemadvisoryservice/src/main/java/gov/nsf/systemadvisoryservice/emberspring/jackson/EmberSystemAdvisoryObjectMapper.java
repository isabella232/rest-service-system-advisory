/**
 * 
 */
package gov.nsf.systemadvisoryservice.emberspring.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * @author KGURUGUB
 *
 */
public class EmberSystemAdvisoryObjectMapper extends ObjectMapper{
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmberSystemAdvisoryObjectMapper() {
	    	//indent the json output so it is easier to read
	    	configure(SerializationFeature.INDENT_OUTPUT, true);
	        
	        //Wite/Read dates as ISO Strings
	        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	    }

}
