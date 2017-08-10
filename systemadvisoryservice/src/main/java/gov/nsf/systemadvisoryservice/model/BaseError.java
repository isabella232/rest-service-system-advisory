package gov.nsf.systemadvisoryservice.model;

/**
 * Class for encapsulating errors for responses
 * 
 * @author jlinden
 *
 */
public class BaseError {

	private final String fieldId;
	private final String detail;

	public String getFieldId() {
		return fieldId;
	}

	public String getDetail() {
		return detail;
	}

	public BaseError() {
		super();
		this.fieldId = null;
		this.detail = null;
	}

	public BaseError(String id, String message) {
		super();
		this.fieldId = id;
		this.detail = message;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BaseError [id=");
		builder.append(fieldId);
		builder.append(", detail=");
		builder.append(detail);
		builder.append("]");
		builder.append("\n");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((detail == null) ? 0 : detail.hashCode());
		result = prime * result + ((fieldId == null) ? 0 : fieldId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if( this.getClass() != obj.getClass() ){
			return false;
		}
		BaseError other = (BaseError) obj;
		return this.fieldId.equals(other.fieldId) && this.detail.equals(other.detail);
	}

}
