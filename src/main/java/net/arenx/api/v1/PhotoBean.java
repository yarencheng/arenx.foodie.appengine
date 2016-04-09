package net.arenx.api.v1;

import com.google.api.server.spi.config.ApiResourceProperty;

public class PhotoBean extends BaseBean{
	
	@ApiResourceProperty(name="name")
	private String description;
	
	@ApiResourceProperty(name="location")
	private LocationBean location;
	
	@ApiResourceProperty(name="user")
	private UserBean user;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public final LocationBean getLocation() {
		return location;
	}

	public final void setLocation(LocationBean location) {
		this.location = location;
	}

	public final UserBean getUser() {
		return user;
	}

	public final void setUser(UserBean user) {
		this.user = user;
	}
	
	
}
