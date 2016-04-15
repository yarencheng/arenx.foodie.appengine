package arenx.foodie.appengine.bean;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Test;

import com.google.common.collect.ImmutableCollection;

import net.arenx.api.bean.LocationBean;
import net.arenx.api.bean.PhotoBean;
import net.arenx.api.bean.PositionBean;
import net.arenx.api.bean.UserBean;

public class LocationBeanTest {

	@Test
	public void LocationBean() {
		LocationBean b1 = new LocationBean();
		LocationBean b2 = new LocationBean(b1);
		assertTrue(EqualsBuilder.reflectionEquals(b1, b2, true));
	}
	
	@Test
	public void set_getUser() {
		LocationBean b = new LocationBean();
		UserBean e = new UserBean();
		b.setUser(e);
		UserBean a = b.getUser();
		assertTrue(EqualsBuilder.reflectionEquals(e, a, true));
		assertTrue(e != a);
	}
	
	@Test
	public void set_getUser_null() {
		LocationBean b = new LocationBean();
		UserBean a = b.getUser();
		assertNull(a);
	}
	
	@Test
	public void set_getPosition() {
		LocationBean b = new LocationBean();
		PositionBean e = new PositionBean();
		b.setPosition(e);
		PositionBean a = b.getPosition();
		assertTrue(EqualsBuilder.reflectionEquals(e, a, true));
		assertTrue(e != a);
	}
	
	@Test
	public void set_getPosition_null() {
		LocationBean b = new LocationBean();
		PositionBean a = b.getPosition();
		assertNull(a);
	}
	
	@Test
	public void getPhotos() {
		LocationBean b = new LocationBean();
		List<PhotoBean>e=b.getPhotos();
		assertTrue(e instanceof ImmutableCollection);
	}
	
	@Test
	public void setPhotos() {
		LocationBean b = new LocationBean();
		List<PhotoBean>e=new ArrayList<PhotoBean>(Arrays.asList(new PhotoBean()));
		b.setPhotos(e);
		List<PhotoBean>a=b.getPhotos();
		assertEquals(e.size(), a.size());
	}
}
