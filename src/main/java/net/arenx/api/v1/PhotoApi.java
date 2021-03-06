package net.arenx.api.v1;

import java.util.List;
import java.util.logging.Logger;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.Nullable;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

import net.arenx.Constants;
import net.arenx.LocationManager;
import net.arenx.PhotoManager;
import net.arenx.UserManager;
import net.arenx.api.bean.PhotoBean;
import net.arenx.api.bean.UserBean;
import net.arenx.jdo.LocationJDO;
import net.arenx.jdo.PhotoJDO;
import net.arenx.jdo.UserJDO;
import static com.google.common.base.Preconditions.*;

@Api(name = "photo", version = "v1")
public class PhotoApi{

	private static final Logger log = Logger.getLogger(PhotoApi.class.getName());
	
	@ApiMethod(
			scopes={Constants.EMAIL_SCOPE},
			clientIds = {
					"755058913802-g2atj31r9k53k9mnkg8e4qsjppj6vj23.apps.googleusercontent.com",
					com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID
					},
			httpMethod = HttpMethod.POST,
			path = "add/{location_id}"
			)
	public PhotoBean add(
			User user,
			@Named("location_id") Long location_id,
			@Nullable @Named("description") String description
			) throws OAuthRequestException {
		if (null == user) {
			throw new OAuthRequestException("need login");
		}
		
		PhotoManager pm = PhotoManager.instance();
		UserManager um=UserManager.instance();
		UserBean ub = um.get(user);
		
		PhotoBean photo = pm.add(ub.getId(), location_id, description);
		return photo;
	}
	
	@ApiMethod(
			scopes={Constants.EMAIL_SCOPE},
			clientIds={
					com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID
					}
			)
	public void like(
			User user,
			@Named("photo_id") Long photoId
			) throws OAuthRequestException {
		if (null == user) {
			throw new OAuthRequestException("need login");
		}
		
		PhotoManager pm = PhotoManager.instance();
		UserManager um=UserManager.instance();
		
		UserBean ub = um.get(user);
		
		pm.like(ub.getId(), photoId);
	}

	@ApiMethod(
			scopes={Constants.EMAIL_SCOPE},
			clientIds={
					com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID
					}
			)
	public void dislike(
			User user,
			@Named("id") Long id
			) throws OAuthRequestException {
		if (null == user) {
			throw new OAuthRequestException("need login");
		}

		PhotoManager pm = PhotoManager.instance();
		UserManager um=UserManager.instance();
		
		UserBean ub = um.get(user);
		
		pm.dislike(ub.getId(), id);
	}

	@ApiMethod(
			scopes={Constants.EMAIL_SCOPE},
			clientIds={
					com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID
					}
			)
	public PhotoBean get(
			@Named("id") Long id
			) {

		PhotoManager pm = PhotoManager.instance();

		PhotoBean photo = pm.get(id);
		if (null == photo) {
			throw new IllegalArgumentException("no such photo");
		}
		
		return photo;
	}
}
