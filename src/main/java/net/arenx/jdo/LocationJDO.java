package net.arenx.jdo;

import java.awt.Point;
import java.util.logging.Logger;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.datanucleus.annotations.Unowned;

import net.arenx.api.bean.LocationBean;
import net.arenx.api.bean.PositionBean;
import net.arenx.api.v1.LocationApi;

@PersistenceCapable(table="location")
public class LocationJDO extends BaseJDO{

	private static final Logger log = Logger.getLogger(LocationJDO.class.getName());

	@Persistent(column="google_place_id")
	private String googlePlaceId;
	
	@Persistent(column="position")
	private GeoPt position;
	
	@Persistent(column="user_id",defaultFetchGroup="true")
	@Unowned
	private UserJDO user;
	
	public UserJDO getUser() {
		return user;
	}

	public void setUser(UserJDO user) {
		this.user = user;
	}
	
	public String getGooglePlaceId() {
		return googlePlaceId;
	}

	public void setGooglePlaceId(String googlePlaceId) {
		this.googlePlaceId = googlePlaceId;
	}

	public final GeoPt getPosition() {
		if (null != position) {
			return new GeoPt(position.getLatitude(), position.getLongitude());
		}
		return null;
	}

	public final void setPosition(GeoPt position) {
		this.position = new GeoPt(position.getLatitude(), position.getLongitude());
	}

	public LocationBean toBean(){		
		LocationBean bean = super.toBean(LocationBean.class);
		
		bean.setGooglePlaceId(googlePlaceId);
		if (null != position){
			PositionBean p =new PositionBean();
			p.setLatitude(position.getLatitude());
			p.setLongitude(position.getLongitude());
			bean.setPosition(p);
		}
		if (null != user)bean.setUser(user.toBean());
		
		return bean;
	}
	
	@Override
	public String toString() {
		return "LocationJDO [googlePlaceId=" + googlePlaceId + ", getId()=" + getId() + ", getCreateDate()=" + getCreateDate() + "]";
	}
}
