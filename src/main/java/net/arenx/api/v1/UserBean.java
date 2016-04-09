package net.arenx.api.v1;

import com.google.api.server.spi.config.ApiResourceProperty;

public class UserBean extends BaseBean{

	@ApiResourceProperty(name="email")
	private String email;

	public final String getEmail() {
		return email;
	}

	public final void setEmail(String email) {
		this.email = email;
	}
	
	
}
