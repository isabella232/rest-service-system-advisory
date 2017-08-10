package gov.nsf.systemadvisoryservice.model;

import javax.annotation.Generated;



import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * System Advisory Schema
 * 
 * This schema should be used to retrieve System advisories
 * 
 */
@SuppressWarnings("restriction")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "Id", "appName", "appAreaName", "advisoryType", "advisoryTitle", "advisoryText", "advisoryURL", "advisoryEffectiveDate", "advisoryExpiryDate", "inputProgram", "inputUser", "inputTimeStamp", "lastUpdateProgram", "lastUpdateUser", "lastUpdateTimeStamp" })
public class SystemAdvisory {

	/**
	 * Unique meeting identifier
	 * 
	 */
	private String Id;
	/**
	 * Application Name
	 * 
	 */
	private String appName;
	/**
	 * Application Area Name
	 * 
	 */
	private String appAreaName;

	/**
	 * Advisory Type
	 * 
	 */
	private String advisoryType;
	/**
	 * Advisory Title
	 * 
	 */
	private String advisoryTitle;
	/**
	 * Details on the advisory
	 * 
	 */
	private String advisoryText;
	/**
	 * Advisory URL if any
	 * 
	 */
	private String advisoryURL;
	/**
	 * Advisory effective date
	 * 
	 */
	private String advisoryEffectiveDate;
	/**
	 * Advisory expiry date
	 * 
	 */
	private String advisoryExpiryDate;
	/**
	 * Program that entered the advisory
	 * 
	 */
	private String inputProgram;
	/**
	 * User that entered the advisory
	 * 
	 */
	private String inputUser;
	/**
	 * Timestamp the user entered the advisory
	 * 
	 */
	private String inputTimeStamp;
	/**
	 * Last updated program
	 * 
	 */
	private String lastUpdateProgram;
	/**
	 * Last updated user
	 * 
	 */
	private String lastUpdateUser;

	private String lastUpdateTimestamp;

	public String getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}

	public void setLastUpdateTimestamp(String lastUpdateTimestamp) {
		this.lastUpdateTimestamp = lastUpdateTimestamp;
	}

	public SystemAdvisory() {
		super();
	}

	/**
	 * Unique meeting identifier
	 * 
	 * @return The Id
	 */

	public String getId() {
		return Id;
	}

	/**
	 * Unique meeting identifier
	 * 
	 * @param Id
	 *            The Id
	 */

	public void setId(String Id) {
		this.Id = Id;
	}

	/**
	 * Application Name
	 * 
	 * @return The appName
	 */

	public String getAppName() {
		return appName;
	}

	/**
	 * Application Name
	 * 
	 * @param appName
	 *            The appName
	 */

	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * Application Area Name
	 * 
	 * @return The appAreaName
	 */
	public String getAppAreaName() {
		return appAreaName;
	}

	/**
	 * Application Area Name
	 * 
	 * @param appAreaName
	 *            The appAreaName
	 */
	public void setAppAreaName(String appAreaName) {
		this.appAreaName = appAreaName;
	}

	/**
	 * Advisory Type
	 * 
	 * @return The advisoryType
	 */
	public String getAdvisoryType() {
		return advisoryType;
	}

	/**
	 * Advisory Type
	 * 
	 * @param advisoryType
	 *            The advisoryType
	 */
	public void setAdvisoryType(String advisoryType) {
		this.advisoryType = advisoryType;
	}

	/**
	 * Advisory Title
	 * 
	 * @return The advisoryTitle
	 */
	public String getAdvisoryTitle() {
		return advisoryTitle;
	}

	/**
	 * Advisory Title
	 * 
	 * @param advisoryTitle
	 *            The advisoryTitle
	 */
	public void setAdvisoryTitle(String advisoryTitle) {
		this.advisoryTitle = advisoryTitle;
	}

	/**
	 * Details on the advisory
	 * 
	 * @return The advisoryText
	 */
	public String getAdvisoryText() {
		return advisoryText;
	}

	/**
	 * Details on the advisory
	 * 
	 * @param advisoryText
	 *            The advisoryText
	 */
	public void setAdvisoryText(String advisoryText) {
		this.advisoryText = advisoryText;
	}

	/**
	 * Advisory URL if any
	 * 
	 * @return The advisoryURL
	 */
	public String getAdvisoryURL() {
		return advisoryURL;
	}

	/**
	 * Advisory URL if any
	 * 
	 * @param advisoryURL
	 *            The advisoryURL
	 */
	public void setAdvisoryURL(String advisoryURL) {
		this.advisoryURL = advisoryURL;
	}

	/**
	 * Advisory effective date
	 * 
	 * @return The advisoryEffectiveDate
	 */
	public String getAdvisoryEffectiveDate() {
		return advisoryEffectiveDate;
	}

	/**
	 * Advisory effective date
	 * 
	 * @param advisoryEffectiveDate
	 *            The advisoryEffectiveDate
	 */
	public void setAdvisoryEffectiveDate(String advisoryEffectiveDate) {
		this.advisoryEffectiveDate = advisoryEffectiveDate;
	}

	/**
	 * Advisory expiry date
	 * 
	 * @return The advisoryExpiryDate
	 */
	public String getAdvisoryExpiryDate() {
		return advisoryExpiryDate;
	}

	/**
	 * Advisory expiry date
	 * 
	 * @param advisoryExpiryDate
	 *            The advisoryExpiryDate
	 */
	public void setAdvisoryExpiryDate(String advisoryExpiryDate) {
		this.advisoryExpiryDate = advisoryExpiryDate;
	}

	/**
	 * Program that entered the advisory
	 * 
	 * @return The inputProgram
	 */
	public String getInputProgram() {
		return inputProgram;
	}

	/**
	 * Program that entered the advisory
	 * 
	 * @param inputProgram
	 *            The inputProgram
	 */
	public void setInputProgram(String inputProgram) {
		this.inputProgram = inputProgram;
	}

	/**
	 * User that entered the advisory
	 * 
	 * @return The inputUser
	 */
	public String getInputUser() {
		return inputUser;
	}

	/**
	 * User that entered the advisory
	 * 
	 * @param inputUser
	 *            The inputUser
	 */
	public void setInputUser(String inputUser) {
		this.inputUser = inputUser;
	}

	/**
	 * Timestamp the user entered the advisory
	 * 
	 * @return The inputTimeStamp
	 */
	public String getInputTimeStamp() {
		return inputTimeStamp;
	}

	/**
	 * Timestamp the user entered the advisory
	 * 
	 * @param inputTimeStamp
	 *            The inputTimeStamp
	 */
	public void setInputTimeStamp(String inputTimeStamp) {
		this.inputTimeStamp = inputTimeStamp;
	}

	/**
	 * Last updated program
	 * 
	 * @return The lastUpdateProgram
	 */
	public String getLastUpdateProgram() {
		return lastUpdateProgram;
	}

	/**
	 * Last updated program
	 * 
	 * @param lastUpdateProgram
	 *            The lastUpdateProgram
	 */
	public void setLastUpdateProgram(String lastUpdateProgram) {
		this.lastUpdateProgram = lastUpdateProgram;
	}

	/**
	 * Last updated user
	 * 
	 * @return The lastUpdateUser
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * Last updated user
	 * 
	 * @param lastUpdateUser
	 *            The lastUpdateUser
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	@Override
	public String toString() {
		return "SystemAdvisory [Id=" + Id + ", appName=" + appName + ", appAreaName=" + appAreaName + ", advisoryType=" + advisoryType + ", advisoryTitle=" + advisoryTitle + ", advisoryText=" + advisoryText + ", advisoryURL=" + advisoryURL + ", advisoryEffectiveDate=" + advisoryEffectiveDate
				+ ", advisoryExpiryDate=" + advisoryExpiryDate + ", inputProgram=" + inputProgram + ", inputUser=" + inputUser + ", inputTimeStamp=" + inputTimeStamp + ", lastUpdateProgram=" + lastUpdateProgram + ", lastUpdateUser=" + lastUpdateUser + "]";
	}

}
