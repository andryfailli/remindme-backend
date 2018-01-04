package it.andreafailli.remindme.common.models;

import org.apache.commons.lang3.StringUtils;
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
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof BaseEntity)) {
			return false;
		}
		BaseEntity other = (BaseEntity) obj;
		return StringUtils.equals(this.getId(), other.getId());
		
	}
	
	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
