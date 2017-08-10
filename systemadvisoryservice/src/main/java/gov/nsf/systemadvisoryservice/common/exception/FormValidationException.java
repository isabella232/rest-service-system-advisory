package gov.nsf.systemadvisoryservice.common.exception;

import gov.nsf.systemadvisoryservice.model.BaseError;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class FormValidationException extends Exception {

	private static final long serialVersionUID = 1L;
	private final List<BaseError> validationErrors;
	
	public FormValidationException(){
		super();
		this.validationErrors = null;
	}
	
	public FormValidationException(List<BaseError> errors){
		super();
		this.validationErrors = errors;
	}
	
	public FormValidationException(String message, List<BaseError> errors){
		super(message);
		this.validationErrors = errors;
	}

	public List<BaseError> getValidationErrors() {
		return validationErrors;
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException{
		//Sonar compliance
	}
	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
		//Sonar compliance
	}
}
