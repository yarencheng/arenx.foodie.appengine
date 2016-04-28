package net.arenx.api.bean;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.google.api.server.spi.config.ApiResourceProperty;

public class PhotoBean extends IdBean{
	
	@ApiResourceProperty(name="description")
	private String description;
	
	@ApiResourceProperty(name="location")
	private LocationBean location;
	
	@ApiResourceProperty(name="user")
	private UserBean user;
	
	@ApiResourceProperty(name="upload_url")
	private String uploadUrl;
	
	@ApiResourceProperty(name="url")
	private String url;
	
	public PhotoBean() {

	}

	public PhotoBean(PhotoBean bean) {
		try {
			BeanUtils.copyProperties(this, bean);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException("Failed to copy property", e);
		}
	}

	public String getUploadUrl() {
		return uploadUrl;
	}

	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocationBean getLocation() {
		if (null == location) {
			return null;
		}
		return new LocationBean(location);
	}

	public void setLocation(LocationBean location) {
		if (null == location) {
			this.location = null;
		} else {
			this.location = new LocationBean(location);
		}
	}

	public UserBean getUser() {
		if (null == user) {
			return null;
		}
		return new UserBean(user);
	}

	public void setUser(UserBean user) {
		if (null == user) {
			this.user = null;
		} else {
			this.user = new UserBean(user);
		}
	}
	
	
}
