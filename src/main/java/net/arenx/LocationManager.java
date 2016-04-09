package net.arenx;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.google.appengine.api.datastore.GeoPt;

import net.arenx.api.v1.LocationBean;
import net.arenx.api.v1.PositionBean;
import net.arenx.jdo.LikeLocationJDO;
import net.arenx.jdo.LocationJDO;
import net.arenx.jdo.UserJDO;

public class LocationManager {

	private static final Logger log = Logger.getLogger(LocationManager.class.getName());
	private static final LocationManager instance = new LocationManager();

	public static LocationManager instance() {
		return instance;
	}

	private LocationManager() {
	}

	public LocationBean get(Long id){
		checkNotNull(id);

		PersistenceManager pm = PMF.get().getPersistenceManager();
	
		try {
			LocationJDO location = pm.getObjectById(LocationJDO.class, id);
			return location.toBean();
		} catch (JDOObjectNotFoundException e) {
			return null;
		}finally {
			pm.close();
		}
	}

	public LocationBean getByGooglePlaceId(String googlePlaceId) {
		checkNotNull(googlePlaceId);
		
		PersistenceManager pm = PMF.get().getPersistenceManager();

		try {
			Query q = pm.newQuery(LocationJDO.class);
			q.setFilter("googlePlaceId == id");
			q.declareParameters("String id");
			List<LocationJDO> locations = (List<LocationJDO>) q.execute(googlePlaceId);
			if (1 < locations.size()) {
				throw new IllegalStateException("more than one entities are found with google_place_id: "+googlePlaceId);
			} else if (1 == locations.size()) {
				return locations.get(0).toBean();
			} else {
				return null;
			}
		} finally {
			pm.close();
		}
	}
	
	public LocationBean createByGooglePlaceId(String googlePlaceId, PositionBean position, Long userId) {
		checkNotNull(googlePlaceId);
		checkNotNull(position);
		checkNotNull(position.getLatitude());
		checkNotNull(position.getLongitude());
		checkNotNull(userId);

		PersistenceManager pm = PMF.get().getPersistenceManager();
		LocationJDO location = new LocationJDO();
		location.setGooglePlaceId(googlePlaceId);
		
		GeoPt g = new GeoPt(position.getLatitude(), position.getLongitude());
		location.setPosition(g);
		
		UserJDO user;
		try {
			user = pm.getObjectById(UserJDO.class, userId);
		} catch (JDOObjectNotFoundException e) {
			throw new IllegalArgumentException("no such user", e);
		}
		
		location.setUser(user);
		
		try {
			pm.makePersistent(location);
			return location.toBean();
		} finally {
			pm.close();
		}
		
	}
	
	public void like(Long userId, Long locationId){
		like(userId, locationId, true);
	}
	
	public void dislike(Long userId, Long locationId){
		like(userId, locationId, false);
	}
	
	private void like(Long userId, Long locationId, boolean isLike) {
		checkNotNull(userId);
		checkNotNull(locationId);

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Query q = pm.newQuery(LikeLocationJDO.class, "user == userParam && location == locationParam");
			q.declareParameters(Long.class.getName() + " userParam, " + Long.class.getName() + " locationParam");
			List<LikeLocationJDO> likes = (List<LikeLocationJDO>) q.execute(userId, locationId);
			LikeLocationJDO like = null;
			if (1 < likes.size()) {
				throw new IllegalStateException("more than one entities are found with [user_id: " + userId + ", photo_id: " + locationId + "]");
			} else if (1 == likes.size()) {
				like = likes.get(0);
			} else {
				like = new LikeLocationJDO();
				
				UserJDO user;
				try {
					user = pm.getObjectById(UserJDO.class, userId);
				} catch (JDOObjectNotFoundException e) {
					throw new IllegalArgumentException("no such user", e);
				}
				like.setUser(user);
				
				LocationJDO lj;
				try {
					lj = pm.getObjectById(LocationJDO.class, locationId);
				} catch (JDOObjectNotFoundException e) {
					throw new IllegalArgumentException("no such location", e);
				}
				like.setLocation(lj);
			}
			
			if (null != like.isLike() && like.isLike() == isLike) {
				return;
			}

			like.setLike(isLike);
			pm.makePersistent(like);
			return;
		} finally {
			pm.close();
		}
	}
}
