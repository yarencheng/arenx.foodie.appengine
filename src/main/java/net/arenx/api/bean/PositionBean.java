package net.arenx.api.bean;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.google.api.server.spi.config.ApiResourceProperty;

public class PositionBean extends BaseBean{

	@ApiResourceProperty(name="latitude")
	private Float latitude;
	
	@ApiResourceProperty(name="longitude")
	private Float longitude;
	
	public PositionBean() {

	}

	public PositionBean(PositionBean bean) {
		try {
			BeanUtils.copyProperties(this, bean);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new RuntimeException("Failed to copy property", e);
		}
	}
	
	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
}
