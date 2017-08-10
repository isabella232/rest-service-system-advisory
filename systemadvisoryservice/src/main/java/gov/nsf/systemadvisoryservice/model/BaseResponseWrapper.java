package gov.nsf.systemadvisoryservice.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Response class wrapper for holding request meta info
 * 
 * @author jlinden
 *
 */
public class BaseResponseWrapper {
	
	private List<BaseError> errors;
	private List<BaseError> warnings;
	private List<BaseError> informationals;
	
	public BaseResponseWrapper() {
		super();
	}
	
	public BaseResponseWrapper(List<BaseError> errors, List<BaseError> warnings, List<BaseError> informationals) {
		this.errors = errors;
		this.warnings = warnings;
		this.informationals = informationals;
	}
	
	public void addError(BaseError error){
		if ( this.errors == null ){
			this.errors = new ArrayList<BaseError>();
		}
			
		this.errors.add(error);
	}
	
	public List<BaseError> getErrors() {
		return errors;
	}
	public void setErrors(List<BaseError> errors) {
		this.errors = errors;
	}
	public List<BaseError> getWarnings() {
		return warnings;
	}
	public void setWarnings(List<BaseError> warnings) {
		this.warnings = warnings;
	}
	public List<BaseError> getInformationals() {
		return informationals;
	}
	public void setInformationals(List<BaseError> informationals) {
		this.informationals = informationals;
	}
	
	@Override
	public String toString(){
		StringBuilder baseResponseWrapperDetails = new StringBuilder();
		baseResponseWrapperDetails.append("Errors :");
		baseResponseWrapperDetails.append(this.getErrors());
		baseResponseWrapperDetails.append("\n");
		
		baseResponseWrapperDetails.append("Warnings :");
		baseResponseWrapperDetails.append(this.getWarnings());
		baseResponseWrapperDetails.append("\n");
		
		baseResponseWrapperDetails.append("Informationals :");
		baseResponseWrapperDetails.append(this.getInformationals());
		baseResponseWrapperDetails.append("\n");
		
		return baseResponseWrapperDetails.toString();
		
	}
	

}
