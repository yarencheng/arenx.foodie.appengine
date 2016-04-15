package net.arenx.api.bean;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.google.api.server.spi.config.ApiResourceProperty;
import com.google.common.collect.ImmutableList;


public class LocationBean extends IdBean{
	
	@ApiResourceProperty(name="google_place_id")
	private String googlePlaceId;
	
	@ApiResourceProperty(name="photos")
	private List<PhotoBean> photos = new ArrayList<PhotoBean>();
	
	@ApiResourceProperty(name="user")
	private UserBean user;
	
	@ApiResourceProperty(name="position")
	private PositionBean position;
	
	public LocationBean() {

	}

	public LocationBean(LocationBean bean) {
		try {
			BeanUtils.copyProperties(this, bean);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException("Failed to copy property", e);
		}
	}
	
	public PositionBean getPosition() {
		if (null == position) {
			return null;
		}
		return new PositionBean(position);
	}

	public void setPosition(PositionBean position) {
		if (null == position) {
			this.position = null;
		} else {
			this.position = new PositionBean(position);
		}
	}

	public List<PhotoBean> getPhotos() {
		return ImmutableList.copyOf(photos);
	}

	public void setPhotos(List<PhotoBean> photos) {
		this.photos.clear();
		this.photos.addAll(photos);
	}

	public String getGooglePlaceId() {
		return googlePlaceId;
	}

	public void setGooglePlaceId(String googlePlaceId) {
		this.googlePlaceId = googlePlaceId;
	}

	public UserBean getUser() {
		if (null == user) {
			return null;
		}else {
			return new UserBean(user);
		}
	}

	public void setUser(UserBean user) {
		if (null == user) {
			this.user = null;
		}else {
			this.user = new UserBean(user);
		}
	}
	
}
