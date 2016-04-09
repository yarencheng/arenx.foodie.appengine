package net.arenx.api.v1;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.Nullable;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.StContainsFilter;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.google.appengine.api.users.User;

import net.arenx.Constants;
import net.arenx.LocationManager;
import net.arenx.PMF;
import net.arenx.UserManager;
import net.arenx.jdo.LocationJDO;
import net.arenx.jdo.UserJDO;
import static com.google.common.base.Preconditions.*;

@Api(name = "location", version = "v1")
public class LocationApi {

	private static final Logger log = Logger.getLogger(LocationApi.class.getName());
	
	@ApiMethod
	public LocationBean getByGooglePlace(
			@Named("google_place_id") String googlePlaceId
			) {
		LocationManager lm = LocationManager.instance();
		
		LocationBean l = lm.getByGooglePlaceId(googlePlaceId);
		if (null == l) {
			throw new IllegalArgumentException("no such location");
		}
				
		return l;
		
		
	}
	
	@ApiMethod
	public void addByGooglePlace(
			User user,
			@Named("google_place_id") String googlePlaceId,
			@Named("position") String position
			) throws OAuthRequestException {
		if (null == user) {
			throw new OAuthRequestException("need login");
		}
		String[] t = position.split(",");
		checkArgument(2 == t.length, "invalid format of position");
		float lat = 0;
		float lon = 0;
		try {
			lat = Float.parseFloat(t[0]);
			lon = Float.parseFloat(t[1]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("invalid format of position", e);
		}
		
		UserManager um=UserManager.instance();
		UserBean userBean = um.get(user);
		
		LocationManager lm = LocationManager.instance();
		
		if (null != lm.getByGooglePlaceId(googlePlaceId)) {
			throw new IllegalArgumentException("allready exist");
		}
		
		PositionBean p = new PositionBean();
		p.setLatitude(lat);
		p.setLongitude(lon);
		lm.createByGooglePlaceId(googlePlaceId, p, userBean.getId());
	}

	@ApiMethod(
			scopes={Constants.EMAIL_SCOPE},
			clientIds={
					com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID
					}
			)
	public void like(
			User user,
			@Named("id") Long id
			) throws OAuthRequestException {
		if (null == user) {
			throw new OAuthRequestException("need login");
		}
		
		LocationManager lm = LocationManager.instance();
		UserManager um=UserManager.instance();
		
		UserBean userBean = um.get(user);
		
		lm.like(userBean.getId(), id);
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
		
		LocationManager lm = LocationManager.instance();
		UserManager um=UserManager.instance();
		
		UserBean userBean = um.get(user);

		lm.dislike(userBean.getId(), id);
	}
}
