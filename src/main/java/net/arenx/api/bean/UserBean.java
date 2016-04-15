package net.arenx.api.bean;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.google.api.server.spi.config.ApiResourceProperty;

public class UserBean extends IdBean{

	@ApiResourceProperty(name="email")
	private String email;

	public UserBean() {

	}

	public UserBean(UserBean bean) {
		try {
			BeanUtils.copyProperties(this, bean);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException("Failed to copy property", e);
		}
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
