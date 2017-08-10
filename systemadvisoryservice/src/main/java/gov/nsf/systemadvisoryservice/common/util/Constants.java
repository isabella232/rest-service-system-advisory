package gov.nsf.systemadvisoryservice.common.util;


public final class Constants {

	private Constants(){
		super();
	}
	
	//DB query statements and parameter constants 
	public static final String LOOKUP_ALL_APP_IDS_QUERY = "select appl_id from msddb.dbo.msd_appl_lkup_vw";
	public static final String LOOKUP_APP_IDS_QUERY = "select appl_id from msddb.dbo.msd_appl_lkup_vw where appl_name in(:names)";
	public static final String GET_ACTIVE_SYSTEM_ADVISORIES_QUERY = "select a.ADVY_ID,c.appl_name,(select appl_name from msddb.dbo.msd_serv_lkup_vw where appl_id =44) as APP_AREA_NAME, a.advy_txt,a.ADVY_TITLE, (CASE WHEN a.appl_id  = 6  THEN 'Information' ELSE b.advy_type_txt END) AS advy_type_txt, a.ADVY_URL,a.ADVY_EFF_DATE,a.advy_exp_date, a.last_updt_pgm,a.last_updt_user,a.last_updt_tmsp ,a.appl_id from flp.appl_advy a ,flp.appl_advy_type_lkup b, msddb.dbo.msd_appl_lkup_vw c where a.advy_type *= b.advy_type_code and a.appl_id = c.appl_id and a.appl_id in(:ids) and a.advy_exp_date >=getdate() order by advy_eff_date desc, last_updt_tmsp desc";
	public static final String GET_INACTIVE_SYSTEM_ADVISORIES_QUERY = "select a.ADVY_ID,c.appl_name,(select appl_name from msddb.dbo.msd_serv_lkup_vw where appl_id =44) as APP_AREA_NAME, a.advy_txt,a.ADVY_TITLE, (CASE WHEN a.appl_id  = 6  THEN 'Information' ELSE b.advy_type_txt END) AS advy_type_txt, a.ADVY_URL,a.ADVY_EFF_DATE,a.advy_exp_date, a.last_updt_pgm,a.last_updt_user,a.last_updt_tmsp ,a.appl_id from flp.appl_advy a ,flp.appl_advy_type_lkup b, msddb.dbo.msd_appl_lkup_vw c where a.advy_type *= b.advy_type_code and a.appl_id = c.appl_id and a.appl_id in(:ids) and a.advy_exp_date <getdate() order by a.advy_exp_date desc, last_updt_tmsp desc";
	public static final String GET_ALL_SYSTEM_ADVISORIES_QUERY = "select a.ADVY_ID,c.appl_name,(select appl_name from msddb.dbo.msd_serv_lkup_vw where appl_id =44) as APP_AREA_NAME, a.advy_txt,a.ADVY_TITLE, (CASE WHEN a.appl_id  = 6  THEN 'Information' ELSE b.advy_type_txt END) AS advy_type_txt, a.ADVY_URL,a.ADVY_EFF_DATE,a.advy_exp_date, a.last_updt_pgm,a.last_updt_user,a.last_updt_tmsp ,a.appl_id from flp.appl_advy a ,flp.appl_advy_type_lkup b, msddb.dbo.msd_appl_lkup_vw c where a.advy_type *= b.advy_type_code and a.appl_id = c.appl_id and a.appl_id in(:ids) order by advy_eff_date desc, last_updt_tmsp desc";
	public static final String POST_SYSTEM_ADVISORY_QUERY = "INSERT INTO flp.appl_advy ( advy_id,advy_type,advy_title,advy_txt,advy_eff_date, advy_exp_date,advy_url,last_updt_pgm,last_updt_user,last_updt_tmsp,appl_id) SELECT MAX(advy_id)+1,(select advy_type_code from flp.appl_advy_type_lkup where advy_type_txt = :advy_type_txt) as advy_type , :advy_title, :advy_txt, :advy_eff_date, :advy_exp_date, null, :last_updt_pgm , :last_updt_user,getdate(),(select appl_id from msddb.dbo.msd_appl_lkup_vw where appl_name = :appl_name) as appl_id from flp.appl_advy";
	public static final String EDIT_SYSTEM_ADVISORY_QUERY = "UPDATE flp.appl_advy set advy_type =(select advy_type_code from flp.appl_advy_type_lkup where advy_type_txt = :advy_type_txt),advy_title = :advy_title,advy_txt = :advy_txt,advy_eff_date = :advy_eff_date,advy_exp_date = :advy_exp_date,advy_url = :advy_url,last_updt_pgm =:last_updt_pgm,last_updt_user =:last_updt_user,last_updt_tmsp = getdate() where advy_id = :advy_id";
	public static final String GET_SYSTEM_ADVISORY_BY_ID_QUERY = "select a.ADVY_ID,c.appl_name,(select appl_name from msddb.dbo.msd_serv_lkup_vw where appl_id =44) as APP_AREA_NAME, a.advy_txt,a.ADVY_TITLE, (CASE WHEN a.appl_id  = 6  THEN 'Information' ELSE b.advy_type_txt END) AS advy_type_txt, a.ADVY_URL,a.ADVY_EFF_DATE,a.advy_exp_date, a.last_updt_pgm,a.last_updt_user,a.last_updt_tmsp ,a.appl_id from flp.appl_advy a ,flp.appl_advy_type_lkup b, msddb.dbo.msd_appl_lkup_vw c where a.advy_type *= b.advy_type_code and a.appl_id = c.appl_id and a.ADVY_ID = :ids";
	public static final String RETRIVE_POST_QUERY = "select a.ADVY_ID,c.appl_name,(select appl_name from msddb.dbo.msd_serv_lkup_vw where appl_id =44) as APP_AREA_NAME, a.advy_txt,a.ADVY_TITLE, (CASE WHEN a.appl_id  = 6  THEN 'Information' ELSE b.advy_type_txt END) AS advy_type_txt, a.ADVY_URL,a.ADVY_EFF_DATE,a.advy_exp_date, a.last_updt_pgm,a.last_updt_user,a.last_updt_tmsp ,a.appl_id from flp.appl_advy a ,flp.appl_advy_type_lkup b, msddb.dbo.msd_appl_lkup_vw c where a.advy_type *= b.advy_type_code and a.appl_id = c.appl_id and a.ADVY_ID  in(select max(advy_id) from flp.appl_advy)";
	public static final String ARCHIVE_SYSTEM_ADVISORY_QUERY = "update flp.appl_advy set advy_exp_date = :advy_exp_date, last_updt_pgm = :last_updt_pgm, last_updt_user = :last_updt_user, last_updt_tmsp = getdate() where advy_id = :advy_id";
	public static final String IDS_PARAMETER = "ids";
	public static final String NAMES_PARAMETER = "names";
	
	//Exception messages
	public static final String SERVER_500_ERROR = "Server Error";
	public static final String SERVER_UNEXPECTED_ERROR = "Unexpected Error";
	public static final String SERVER_RESOURCE_NOT_FOUND = "Resource Not Found";
	public static final String DB_TRANSACTION_ERROR = "DB Transaction Error";
	public static final String INVALID_FORM_DATA = "Invalid/Missing Form Data";
	public static final String APP_ID_LOOKUP_FAILED = "Unable to retreive a corresponding application ID for the provided application name.";
	public static final String ERROR_RETRIEVING_ACTIVE_ADVISORIES = "Error retrieving active system advisories, rolling back the transaction";
	public static final String ERROR_CREATING_NEW_ADVISORY = "";
	public static final String ERROR_QUERYING_APPLICATION_ID_LOOKUP_TABLE = "Error querying the application lookup table";
	public static final String MISSING_MESSAGE = "missing from the form.";
	public static final String ACCESS_DENIED_EXCEPTION = "Access Denied";
	public static final String ERROR_EDITING_ADVISORY = "Error editting a system advisory.";
	public static final String ERROR_RETRIVING_ADVISORY_BY_ID = "Error retrieving system advisories, which recently got updated.";
	public static final String ERROR_RETRIVING_ADVISORY_BY_MAX = "Error retrieving recent system advisories.";
	public static final String ERROR_ARCHIVING_ADVISORY = "Error archiving a system advisory";

	
	//Validation error messages
	public static final String MISSING_APP_NAME = "Application name must be specified";
	public static final String MISSING_APP_AREA_NAME = "Application area name must be specified";
	public static final String MISSING_ADVY_TITLE = "Please enter a value for Advisory Headline";
	public static final String MISSING_ADVY_TYPE = "Please enter a value for Advisory Type";
	public static final String MISSING_ADVY_EFF_DATE = "Please enter a value for Effective Date";
	public static final String MISSING_ADVY_EXPY_DATE = "Please enter a value for End Date";
	public static final String MISSING_LAST_UPDT_USER = "Please enter a value for Last Update User";
	public static final String MISSING_LAST_UPDT_PGM = "Please enter a value for Last Update Program";
	public static final String MISSING_ADVY_ID = "Advisory ID is either missing or is invalid";

	public static final String INVALID_EFF_DATE_FORMAT = "Please use the correct Date/Time format for Effective Date";
	public static final String INVALID_END_DATE_FORMAT = "Please use the correct Date/Time format for End Date";
	public static final String END_DATE_BEFORE_EFF_DATE = "End Date / Time cannot be before the Effective Date / Time";
	public static final String EFF_DATE_BEFORE_CURR_DATE = "Effective Date / Time cannot be before the Current Date / Time";

	
	//SystemAdvisoryRowMapper result set columns
	public static final String ADVY_ID = "advy_id";
	public static final String APP_NAME = "appl_name";
	public static final String APP_AREA_NAME = "app_area_name";
	public static final String ADVY_TYPE = "advy_type_txt";
	public static final String ADVY_TITLE = "advy_title";
	public static final String ADVY_TXT = "advy_txt";
	public static final String ADVY_URL = "advy_url";
	public static final String ADVY_EFF_DATE = "advy_eff_date";
	public static final String ADVY_EXPY_DATE = "advy_exp_date";
	public static final String INPUT_PGM = "MyNSF";
	public static final String INPUT_USER = "sasvc";
	public static final String INPUT_TMSP = "";
	public static final String LAST_UPDT_PGM = "last_updt_pgm";
	public static final String LAST_UPDT_USER = "last_updt_user";
	public static final String LAST_UPDT_TMSP = "last_updt_tmsp";
		
	//Other
	public static final String DATE_TIME_REGEX = "(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2}).0";
	public static final String EMPTY_STRING = "";
	public static final String GET_METHOD_SUMMARY = "Returns system advisories";
	public static final String GET_METHOD_DESCRIPTION = "Retreives system advisories from the DB based on the passed inputs";
	public static final String POST_METHOD_SUMMARY = "Inserts a system advisory into the DB";
	public static final String POST_METHOD_DESCRIPTION = "Inserts the passed system advisory into the DB after validating it for correctness";
	public static final String EDIT_METHOD_SUMMARY = "Edit a system advisory";
	public static final String EDIT_METHOD_DESCRIPTION = "Update the existing system advisory with the new details after validating it for correctness";
	public static final String ARCHIVE_METHOD_SUMMARY = "Archives a system advisory (making it inactive)";
	public static final String ARCHIVE_METHOD_DESCRIPTION = "Achives a system advisory by setting the end date to the current date.";

	public static final String ACTIVE_TRUE = "true";
	public static final String ACTIVE_FALSE = "false";
	public static final String ALL_ADV = "All";
	public static final String SYSTEM_ADVISORY_WRAPPER = "systemAdvisoryWrapper";
	public static final String SYSTEM_ADVISORY = "systemAdvisory";

}
