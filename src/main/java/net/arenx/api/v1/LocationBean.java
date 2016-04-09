package net.arenx.api.v1;
import java.util.List;

import com.google.api.server.spi.config.ApiResourceProperty;
import com.google.appengine.api.datastore.GeoPt;


public class LocationBean extends BaseBean{
	
	@ApiResourceProperty(name="google_place_id")
	private String googlePlaceId;
	
	@ApiResourceProperty(name="photos")
	private List<PhotoBean> photos;
	
	@ApiResourceProperty(name="user")
	private UserBean user;
	
	@ApiResourceProperty(name="position")
	private PositionBean position;
	
	public final PositionBean getPosition() {
		if (null == position) {
			return null;
		}
		PositionBean p =new PositionBean();
		p.setLatitude(position.getLatitude());
		p.setLongitude(position.getLongitude());
		return p;
	}

	public final void setPosition(PositionBean position) {
		if (null == this.position) {
			this.position = new PositionBean();
		}
		this.position.setLatitude(position.getLatitude());
		this.position.setLongitude(position.getLongitude());
	}

	public List<PhotoBean> getPhotos() {
		return photos;
	}

	public void setPhotos(List<PhotoBean> photos) {
		this.photos = photos;
	}

	public String getGooglePlaceId() {
		return googlePlaceId;
	}

	public void setGooglePlaceId(String googlePlaceId) {
		this.googlePlaceId = googlePlaceId;
	}

	public final UserBean getUser() {
		return user;
	}

	public final void setUser(UserBean user) {
		this.user = user;
	}
	
}
