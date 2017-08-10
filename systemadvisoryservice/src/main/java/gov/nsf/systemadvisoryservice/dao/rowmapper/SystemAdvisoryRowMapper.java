package gov.nsf.systemadvisoryservice.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import gov.nsf.systemadvisoryservice.common.util.Constants;
import gov.nsf.systemadvisoryservice.model.SystemAdvisory;

import org.springframework.jdbc.core.RowMapper;

/**
 * Implementation of RowMapper interface for MyNSF advisories (used by JdbcTemplate)
 * @author jlinden
 *
 */
public class SystemAdvisoryRowMapper implements RowMapper<SystemAdvisory> {

	@Override
	public SystemAdvisory mapRow(ResultSet rs, int rowNum) throws SQLException {
		SystemAdvisory sysAdv = new SystemAdvisory();
		
		sysAdv.setId(rs.getString(Constants.ADVY_ID));
		sysAdv.setAppName(rs.getString(Constants.APP_NAME)  != null ? rs.getString(Constants.APP_NAME).trim() : "");
		sysAdv.setAppAreaName(rs.getString(Constants.APP_AREA_NAME) != null ? rs.getString(Constants.APP_AREA_NAME).trim() : "");
		sysAdv.setAdvisoryType(rs.getString(Constants.ADVY_TYPE) != null ? rs.getString(Constants.ADVY_TYPE).trim() : "");
		sysAdv.setAdvisoryTitle(rs.getString(Constants.ADVY_TITLE) != null ? rs.getString(Constants.ADVY_TITLE).trim() : "");
		sysAdv.setAdvisoryText(rs.getString(Constants.ADVY_TXT) != null ? rs.getString(Constants.ADVY_TXT).trim() : "");
		sysAdv.setAdvisoryURL(rs.getString(Constants.ADVY_URL) != null ? rs.getString(Constants.ADVY_URL).trim() : "");
		sysAdv.setAdvisoryEffectiveDate(rs.getString(Constants.ADVY_EFF_DATE));
		sysAdv.setAdvisoryExpiryDate(rs.getString(Constants.ADVY_EXPY_DATE));
		sysAdv.setInputProgram(Constants.INPUT_PGM);
		sysAdv.setInputUser(Constants.INPUT_USER);
		sysAdv.setInputTimeStamp(Constants.INPUT_TMSP);
		sysAdv.setLastUpdateProgram(rs.getString(Constants.LAST_UPDT_PGM));
		sysAdv.setLastUpdateUser(rs.getString(Constants.LAST_UPDT_USER));
		sysAdv.setLastUpdateTimestamp(rs.getString(Constants.LAST_UPDT_TMSP));
		return sysAdv;	

	}

}
