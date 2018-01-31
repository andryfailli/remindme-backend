package it.andreafailli.remindme.common.models;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Id;

public abstract class BaseEntity {

	@Id
	private String id;

	public BaseEntity() {}
	
	public BaseEntity(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String  id) {
		this.id = id;
	}	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof BaseEntity))
			return false;
		BaseEntity other = (BaseEntity) obj;
		if (this.id == null) {
			return false;
		} else return this.id.equals(other.getId());
	}
	
	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
