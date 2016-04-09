package net.arenx.api.v1;

import java.util.Date;

import com.google.api.server.spi.config.ApiResourceProperty;

public class BaseBean {

	@ApiResourceProperty(name="id")
	private Long id;
	
	@ApiResourceProperty(name="create_date")
	private Date createDate;
	
	public final Long getId() {
		return id;
	}
	public final void setId(Long id) {
		this.id = id;
	}
	public final Date getCreateDate() {
		if (null != createDate) {
			return (Date) createDate.clone();
		}
		return null;
	}
	public final void setCreateDate(Date createDate) {
		if (null != createDate) {
			this.createDate = (Date) createDate.clone();
		}
		this.createDate = null;
	}
	
}
