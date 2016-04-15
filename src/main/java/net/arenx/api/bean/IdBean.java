package net.arenx.api.bean;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;

import com.google.api.server.spi.config.ApiResourceProperty;

public class IdBean extends BaseBean {

	@ApiResourceProperty(name = "id")
	private Long id;

	@ApiResourceProperty(name = "create_date")
	private Date createDate;
	
	public IdBean() {

	}

	public IdBean(IdBean bean) {
		try {
			BeanUtils.copyProperties(this, bean);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException("Failed to copy property", e);
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateDate() {
		if (null != createDate) {
			return (Date) createDate.clone();
		}
		return null;
	}

	public void setCreateDate(Date createDate) {
		if (null != createDate) {
			this.createDate = (Date) createDate.clone();
		} else {
			this.createDate = null;
		}
	}

	@Override
	public String toString() {
		return "IdBean [id=" + id + ", createDate=" + createDate + "]";
	}

}
