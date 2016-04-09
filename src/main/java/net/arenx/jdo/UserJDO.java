package net.arenx.jdo;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import net.arenx.api.v1.UserBean;

@PersistenceCapable(table="user")
public class UserJDO extends BaseJDO{

	

	@Persistent(column="appengine_id")
	private String appengineId;

	@Persistent(column="email")
	private String email;

	
	public String getAppengineId() {
		return appengineId;
	}

	public void setAppengineId(String appengineId) {
		this.appengineId = appengineId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public UserBean toBean(){		
		UserBean bean = super.toBean(UserBean.class);
		
		bean.setEmail(email);
		
		return bean;
	}
	
	@Override
	public String toString() {
		return "UserJDO [appengineId=" + appengineId + ", email=" + email + ", getId()=" + getId() + ", getCreateDate()=" + getCreateDate() + "]";
	}
}
