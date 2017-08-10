package gov.nsf.systemadvisoryservice.common;

import gov.nsf.systemadvisoryservice.common.util.Constants;
import gov.nsf.systemadvisoryservice.model.SystemAdvisory;

import java.util.ArrayList;

public class TestUtils {
	
	/**
	 * Variadic method that returns a list of SystemAdvisory objects with id's specified by the arguments
	 * to be used as mock objects 
	 * 
	 * @param ids - variable number of int ids passed in
	 * @return - ArrayList<SystemAdvisory> resulting list of System Advisory objects
	 */
	public static ArrayList<SystemAdvisory> getMockAdvisories(int ... ids){
		ArrayList<SystemAdvisory> advisories = new ArrayList<SystemAdvisory>();
		
		for( int id : ids ){
			SystemAdvisory sysAdv = new SystemAdvisory();
			sysAdv.setAppName(Constants.APP_NAME);
			sysAdv.setAppAreaName(Constants.APP_AREA_NAME);
			sysAdv.setAdvisoryType(Constants.ADVY_TYPE);
			sysAdv.setAdvisoryTitle(Constants.ADVY_TITLE);
			sysAdv.setAdvisoryText(Constants.ADVY_TXT);
			sysAdv.setAdvisoryURL(Constants.ADVY_URL);
			sysAdv.setAdvisoryEffectiveDate(Constants.ADVY_EFF_DATE);
			sysAdv.setAdvisoryExpiryDate(Constants.ADVY_EXPY_DATE);
			sysAdv.setInputProgram(Constants.INPUT_PGM);
			sysAdv.setInputUser(Constants.INPUT_USER);
			sysAdv.setInputTimeStamp(Constants.INPUT_TMSP);
			sysAdv.setLastUpdateProgram(Constants.LAST_UPDT_PGM);
			sysAdv.setLastUpdateUser(Constants.LAST_UPDT_USER);
			sysAdv.setLastUpdateTimestamp(Constants.LAST_UPDT_TMSP);
			sysAdv.setId(""+id);
			advisories.add(sysAdv);
		}
		
		
		return advisories;	
		
	}
	
	
	

}
