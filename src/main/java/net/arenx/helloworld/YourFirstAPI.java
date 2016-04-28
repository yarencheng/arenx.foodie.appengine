package net.arenx.helloworld;

import java.util.logging.Logger;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.Nullable;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

import net.arenx.Constants;
import net.arenx.UserManager;
import net.arenx.api.bean.UserBean;
import net.arenx.api.v1.LocationApi;

/**
 * Add your first API methods in this class, or you may create another class. In
 * that case, please update your web.xml accordingly.
 **/

@Api(name = "myApi", version = "v1", namespace = @ApiNamespace(ownerDomain = "helloworld.example.com", ownerName = "helloworld.example.com", packagePath = ""))
public class YourFirstAPI {

	private static final Logger log = Logger.getLogger(LocationApi.class.getName());
	
	/** A simple endpoint method that takes a name and says Hi back */
	@ApiMethod(
			name = "sayHi",
			//httpMethod = HttpMethod.GET,
			//audiences = {"755058913802-g2atj31r9k53k9mnkg8e4qsjppj6vj23.apps.googleusercontent.com"},
			clientIds = {
					"755058913802-g2atj31r9k53k9mnkg8e4qsjppj6vj23.apps.googleusercontent.com",
					com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID
					}
			)
	public MyBean sayHi(@Nullable @Named("name") String name,User user) {
		MyBean response = new MyBean();
		response.setData("Hi, " + name+ " " +user);

		//log.warning("user="+user);
		
		return response;
	}
	
	
	@ApiMethod(
			scopes={Constants.EMAIL_SCOPE},
			httpMethod = HttpMethod.POST,
			clientIds = {
					"755058913802-g2atj31r9k53k9mnkg8e4qsjppj6vj23.apps.googleusercontent.com",
					com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID
					}
			)
	public MyBean testpost(
			User user,
			 @Named("name") String name
			) throws OAuthRequestException {
		MyBean response = new MyBean();
		response.setData("post, " + name + ", "+ user);
		return response;
	}
	
	@ApiMethod(
			scopes={Constants.EMAIL_SCOPE},
			httpMethod = HttpMethod.GET,
			clientIds = {
					"755058913802-g2atj31r9k53k9mnkg8e4qsjppj6vj23.apps.googleusercontent.com",
					com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID
					}
			)
	public MyBean testget(
			User user,
			@Nullable @Named("name") String name
			) throws OAuthRequestException {
		MyBean response = new MyBean();
		response.setData("get, " + name + ", "+ user);
		return response;
	}

}