package gov.nsf.systemadvisoryservice.common.exception;

/**
 * RollbackException - custom exception class to throw an exception when 
 * a transaction rolls back due to any errors while serving the DB requests
 * 
 * @author skandaka
 * @version 1.0
 * @created 29-Jan-2016 12:44:11 PM
 */
public class RollbackException extends Exception {
  private static final long serialVersionUID = 1L;
  private final String className;
  private final int errCode;
  private final String errMsg;

  /**
   * Default constructor that inherits the parent qualities
   */
  public RollbackException() {
    super();
    this.className = null;
    this.errCode = 0;
    this.errMsg = null;
  }

  /**
   * Constructor with a param error msg that inherits the parent qualities
   * 
   * @param Exception
   */
  public RollbackException(Exception errorMessage) {
    super(errorMessage);
    this.className = null;
    this.errCode = 0;
    this.errMsg = errorMessage.getMessage();
  }

  public RollbackException(Exception errorMessage, String msg) {
    super(errorMessage);
    this.className = null;
    this.errCode = 0;
    this.errMsg = msg; // Changed to use errMsg instead of customErrorMessage
  }

  /**
   * Constructor with a param error msg that inherits the parent qualities
   * 
   * @param String error message
   */
  public RollbackException(String errorMessage) {
    this.errMsg = errorMessage;
    this.className = null;
    this.errCode = 0;
  }

  /**
   * Constructor with a param error msg that inherits the parent qualities
   * 
   * @param Exception, String classname
   */
  public RollbackException(String className, Exception errorMessage) {
    super(errorMessage);
    this.className = className;
    this.errCode = 0;
    this.errMsg = errorMessage.getMessage();
  }

  /**
   * Constructor with a param error msg that inherits the parent qualities
   * 
   * @param String error message, String classname
   */
  public RollbackException(String className, String errorMessage) {
    this.className = className;
    this.errMsg = errorMessage;
    this.errCode = 0;
  }

  /**
   * Constructor with a param error msg that inherits the parent qualities
   * 
   * @param Exception, String classname, int error code
   */
  public RollbackException(String className, Exception errorMessage, int errorCode) {
    super(errorMessage);
    this.className = className;
    this.errCode = errorCode;
    this.errMsg = errorMessage.getMessage();
  }

  /**
   * Constructor with a param error msg that inherits the parent qualities
   * 
   * @param String error message, String classname, int error code
   */
  public RollbackException(String className, String errorMessage, int errorCode) {
    super(errorMessage);
    this.className = className;
    this.errCode = errorCode;
    this.errMsg = errorMessage;
  }

  /**
   * @return classname
   */
  public String getClassName() {
    return className;
  }

  /**
   * @return error code
   */
  public int getErrCode() {
    return errCode;
  }

  /**
   * @return error message
   */
  public String getErrMsg() {
    return errMsg;
  }

}
